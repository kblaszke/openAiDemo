#!/bin/sh

curl -X POST http://localhost:8000/add-embedding \
  -H "Content-Type: application/json" \
  -d '{
    "collection": "dhamma_talks",
    "ids": ["test1"],
    "embeddings": [[0.1, 0.2, 0.3]],
    "documents": ["this is a test"],
    "metadatas": [{"source": "unit"}]
  }'

