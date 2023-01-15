# SimplePrefixes

<a href="https://wakatime.com/@1f3b44b5-611a-484d-bcdc-b2084eefec1a/projects/qbgzgykwwe"><img src="https://wakatime.com/badge/user/1f3b44b5-611a-484d-bcdc-b2084eefec1a/project/dcbe04ad-7fe2-40a7-9224-5da4400ab0dc.svg" alt="wakatime"></a>

A plugin aiming to allow for simple player-selected prefixes that can be used in chat!

> **Note**
> 
> This plugin is still currently under development. You may compile and use this plugin if you wish,
> but it is very likely to change and break existing versions during development.
> 
> Documentation is made for existing versions, more for our sake.

## Requirements

- Paper-based Server (ie: [PurpurMC](https://purpurmc.org/))
  - This plugin takes advantage of Mini-Message from Paper.
- [PlaceholderAPI Plugin](https://github.com/PlaceholderAPI/PlaceholderAPI)

## Features



## Placeholders, Commands, and Permissions

`%sp_prefix%`
> Placeholder for the prefix. Use this in your chat plugin.
> 
> Example Usage: `%sp_prefix% %player_displayname% » {message}`

`/sp reset`
> Resets your prefix.

`/sp set <prefix-id>`
> Sets your prefix.

## Configuration

```yml
saving-type: "file"
default-prefix: "<white>[<gray>Player</gray>]</white> "

prefix-id:
  display-name: "Prefix ID"
  description:
    - "This is to show an example Prefix!"
    - "This is a second line of description!"
  prefix: "<white>[<rainbow>Something</rainbow>]</white> "
  verify-always: false
  show-when-locked: true
  requirements:
    - "permission simpleprefix.example true"
    - "statistic PLAYER_KILLS >= 10"
    - "advancement nether/summon_wither true"
    - "compare_str %placeholder% == string"
    - "compare_int %placeholder% < 1"
```

### Configuration Settings

| Setting     | Description                                 | Valid Values |
|-------------|---------------------------------------------|--------------|
| saving-type | What type of saving system should this use? | `PDC`, `FILE` |
| default-prefix | Fallback prefix, supports Placeholders. | String |

#### Saving Types

These are the current implementations of the saving types...

__PersistentDataContainer (PDC)__
> The plugin will save the prefix ID of the currently equipped prefix on the Player themselves.
> This makes the prefix inaccessible while they are offline.
> 
> This was primarily used for initial testing of the plugin but if you don't like file systems for some reason, there.

__YML File (FILE)__
> The plugin will save the prefix IDs of all players to a configuration YML file stored alongside the config.
> This allows for the prefix to be available while they are offline, but any time a player's prefix is changed,
> the plugin saves the prefix.
> 
> This is default.

### Creating Prefixes

`prefix-id`
> This is the Prefix ID. Every prefix is uniquely identified by this value.
> This does mean you cannot have two of the same Prefix IDs.
> 
> The `prefix-id` itself is the value you change. Don't include spaces.

`display-name`
> This is the Display Name of the prefix. It is used as basically the formatted way to display this Prefix.
> 
> Supports Placeholders from PlaceholderAPI.

`prefix`
> This is the String representation of the prefix that will be displayed in place of %sp_prefix%.
>
> Supports Placeholders from PlaceholderAPI.

```yml
# A prefix with the ID "example-prefix"...
example-prefix:
  display-name: "The Example Prefix"
  prefix: "<white>[<rainbow>Example</rainbow>]</white> "
```

## WIP Features

- Chest GUI
- Configurable Icons
- Requirement Checks
