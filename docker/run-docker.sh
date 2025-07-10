#!/bin/sh

docker run -p 8000:8000 -v $(pwd)/chroma-data:/app/chroma-data chroma-rest &
