from fastapi import FastAPI
from pydantic import BaseModel
from typing import List, Dict, Optional
import chromadb
from chromadb.config import Settings

app = FastAPI()

import chromadb

client = chromadb.PersistentClient(path="./chroma-data")

@app.get("/api/v1/heartbeat")
def heartbeat():
    return {"status": "ok"}

class AddEmbeddingRequest(BaseModel):
    collection: str
    ids: List[str]
    embeddings: List[List[float]]
    documents: List[str]
    metadatas: Optional[List[Dict[str, str]]] = None

@app.post("/add-embedding")
def add_embedding(req: AddEmbeddingRequest):
    collection = client.get_or_create_collection(name=req.collection)
    collection.add(
        ids=req.ids,
        embeddings=req.embeddings,
        documents=req.documents,
        metadatas=req.metadatas
    )
    return {"status": "ok"}

class QueryRequest(BaseModel):
    collection: str
    query_embedding: List[float]
    n_results: int = 3

@app.post("/query")
def query(req: QueryRequest):
    collection = client.get_collection(name=req.collection)
    results = collection.query(
        query_embeddings=[req.query_embedding],
        n_results=req.n_results
    )
    return results


@app.delete("/collection/{name}")
def delete_collection(name: str):
    try:
        client.delete_collection(name=name)
        return {"status": "deleted", "collection": name}
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Błąd przy usuwaniu kolekcji: {str(e)}")

@app.get("/api/v1/collections/SpringAiCollection")
def list_collections():
    try:
        collections = client.list_collections()
        return [
            {
                "name": col.name,
                "size": col.count()
            }
            for col in collections
        ]
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Błąd przy pobieraniu kolekcji: {str(e)}")

@app.get("/api/v1/collections/{collection_name}")
def get_collection(collection_name: str):
    # zwróć POJEDYNCZĄ kolekcję jako dict
    return {
        "name": collection_name,
        "id": "abc123",
        "metadata": {}
    }


from pydantic import BaseModel
from typing import List, Optional

class QueryRequest(BaseModel):
    collection: str
    embedding: List[float]
    top_k: int = 5

class QueryResult(BaseModel):
    document: str
    metadata: Optional[dict] = None
    score: Optional[float] = None

@app.post("/query-embedding", response_model=List[QueryResult])
def query_embedding(request: QueryRequest):
    try:
        collection = client.get_collection(name=request.collection)
        results = collection.query(
            query_embeddings=[request.embedding],
            n_results=request.top_k,
            include=['documents', 'metadatas', 'distances']
        )
        return [
            QueryResult(
                document=doc,
                metadata=meta,
                score=1.0 - dist  # im bliżej 1, tym lepsze dopasowanie
            )
            for doc, meta, dist in zip(
                results["documents"][0],
                results["metadatas"][0],
                results["distances"][0]
            )
        ]
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Błąd zapytania: {str(e)}")
