from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import Dict, Optional
import chromadb
from chromadb.config import Settings

app = FastAPI()

# Inicjalizacja klienta z trwałym storage'em
client = chromadb.PersistentClient(path="./chroma-data")

# Model danych kolekcji dla REST API
class CollectionModel(BaseModel):
    name: str
    id: Optional[str] = None
    metadata: Optional[Dict] = {}

@app.get("/api/v1/heartbeat")
def heartbeat():
    return {"status": "ok"}

   
@app.get("/api/v1/collections")
@app.get("/api/v1/collections/SpringAiCollection")
def list_collections():
    collections = client.list_collections()
    return {
        "collections": [
            {
                "name": col.name,
                "id": col.name,
                "metadata": col.metadata or {}
            }
            for col in collections
        ]
    }

@app.get("/api/v1/collections/{collection_name}")
def get_collection(collection_name: str):
    try:
        col = client.get_collection(collection_name)
        return {
            "name": col.name,
            "id": col.name,
            "metadata": col.metadata or {}
        }
    except Exception:
        raise HTTPException(status_code=404, detail="Collection not found")

@app.post("/api/v1/collections")
def create_collection(collection: CollectionModel):
    try:
        col = client.create_collection(
            name=collection.name,
            metadata=collection.metadata
        )
        return {
            "name": col.name,
            "id": col.name,
            "metadata": col.metadata or {}
        }
    except Exception as e:
        raise HTTPException(status_code=409, detail=f"Collection already exists or error: {e}")





from pydantic import BaseModel
from typing import List, Optional

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

