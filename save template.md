# Save game files
This file exists to help under stand how the level is saved and as a visualizer for saving the game

## File structure
Bellow is the basic skeleton for a level named 'My first World'.
```
My Games
 | - Sandbox
 |   | - My first World
 |   |    | - world
 |   |    | - chunk0
 |   |    | - (Any other chunks)
 |   | - (Any other save games)
```
#### world file contents
```json
{
  "seed": (game seed),
  "player": {
    "x": (x location),
    "y": (y location),
    "z": (z location),
  }, 
  "chunks": [{ "id": 0,
    "x": (x location),
    "y": (y location),
    "blend-map": "map.png",
    "texturepack": 0
  }
  (any other chunks, would load as file 'chunk(id)')
  ]
}
```
#### chunk0 file contents
```json
{
  "data": [{"object": true,
    "x": (x position),
    "y": (y position),
    "z": (z position),
    "tex-index": 0 (optional key),
    "modelId": (model id),
  }
  "(any other objects or entities)"
  ]
}
```

## Example levels
There are no current example levels
