# BasicTp

A simple teleportation plugin for Paper 1.21.5.

## Commands

| Command | Description |
|---------|-------------|
| `/tpa <player>` | Request to teleport to a player |
| `/tpahere <player>` | Request a player to teleport to you |
| `/tpaccept [player]` | Accept a teleport request |
| `/tpdeny [player]` | Deny a teleport request |
| `/tpcancel` | Cancel your outgoing request |
| `/tp` | Reload config or show info (admin) |

## Permissions

| Permission | Default | Description |
|------------|---------|-------------|
| `basictp.*` | op | All permissions |
| `basictp.tpa` | true | Use `/tpa` |
| `basictp.tpahere` | true | Use `/tpahere` |
| `basictp.tpaccept` | true | Use `/tpaccept` |
| `basictp.tpdeny` | true | Use `/tpdeny` |
| `basictp.tpcancel` | true | Use `/tpcancel` |
| `basictp.admin` | op | Use `/tp` |

## Config

```yaml
# timeout in seconds before a request expires
request-timeout: 60

# cooldown in seconds between requests
cooldown: 5

# allow cross-world teleportation
allow-cross-world: true

# show particle effects on teleport
particle-effects: true
```

## Build

Requires **Java 21+** and **Maven**.

Run `build.bat`:

```
build.bat
```

The compiled jar will be at `build/BasicTp.jar`.
