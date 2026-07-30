# BasicTp

A Paper 1.21.5 teleportation plugin with requests, accept/deny, and ignore.

## build

run `build.bat` or `mvn clean package` - the jar will be in `build/`.

## usage

1. put the jar in `plugins/` and restart the server
2. use `/tpa <player>` to send a teleport request

## commands

| command | description |
|---------|-------------|
| `/tpa <player>` | request to teleport to a player |
| `/tpahere <player>` | request a player to teleport to you |
| `/tpaccept [player]` (`/tpyes`) | accept a request |
| `/tpdeny [player]` (`/tpno`) | deny a request |
| `/tpcancel` (`/tpacancel`) | cancel your outgoing request |
| `/tplist` | show pending requests |
| `/tpignore <player>` | toggle ignore requests from a player |

## config

default config is created automatically at `plugins/BasicTp/config.yml`.

## requirements

- paper 1.21.5
- java 21
