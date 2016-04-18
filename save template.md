# Save game files
Bellow is the skeleton for a level named 'My first World'

File structure
```
My Games
  - Sandbox
    - My first World
      - world
      - chunk0
      (Any other chunks)
    (Any other save games)
```
world file contents
```json
{
  "seed: (game seed)
  "player": {
    "x": (x location),
    "y": (y location),
    "z": (z location),
  }, 
  "chunks": [{ "id: 0,
    "x": (x location),
    "y": (y location),
    "blend-map": "map.png",
    "texturepack", 0
  }]
}
```
chunk0 file contents
```json
{
  "data": [{"object: true,
    "x": (x position),
    "y": (y position),
    "z": (z position),
    "tex-index": 0 (optional key),
    "modelId": (model id),
    
  }]
}
```
