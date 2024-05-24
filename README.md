# SimplePrefixes

<a href="https://wakatime.com/@1f3b44b5-611a-484d-bcdc-b2084eefec1a/projects/qbgzgykwwe"><img src="https://wakatime.com/badge/user/1f3b44b5-611a-484d-bcdc-b2084eefec1a/project/dcbe04ad-7fe2-40a7-9224-5da4400ab0dc.svg" alt="wakatime"></a>

A plugin aiming to allow for simple player-selected prefixes that can be used in chat!

# Requirements

- Paper-based Server (ie: [PurpurMC](https://purpurmc.org/))
  - This plugin takes advantage of Mini-Message from Paper.
- [PlaceholderAPI Plugin](https://github.com/PlaceholderAPI/PlaceholderAPI)

# Features

<img src='https://user-images.githubusercontent.com/20095065/215312375-0cb1f307-2eae-43c4-bbf9-2b306bad5952.png' width=30%> <img src='https://user-images.githubusercontent.com/20095065/215312448-666f183a-d6a8-4aa0-a643-6694ff52b670.png' width=30%>
<img src='https://github.com/Simplexity-Development/SimplePrefixes/assets/20095065/f6b57b7a-7d2b-4e24-9422-b5ede0cf589e' width=30%>

- Player friendly GUI for selecting nicknames.
- Does not interact with any other plugins directly.
  - The plugin uses and hold prefixes in a placeholder, it will not affect permission groups / meta.
- Supports PlaceholderAPI Placeholders.
- Supports Mini-Message formatting.
- Supports multiple types of requirements: `PERMISSION`, `STATISTIC`, `ADVANCEMENT`, `COMPARE_INT`

# Placeholders, Commands, and Permissions

`%sp_prefix%`
> Placeholder for the prefix. Use this in your chat plugin.
>
> Example Usage: `%sp_prefix% %player_displayname% » {message}`

`%sp_prefix_legacy%`
> Placeholder for the prefix. Still takes Mini-Message format in config.yml / prefixes.yml but converts the format to legacy (`&a` / `§a` instead of `<green>`) for other plugins. This is when you need the legacy way to display stuff, like with TAB Plugin.
>
> This will automatically strip `<hover>` and `<click>` tags.

`/sp gui`
> Opens a generated GUI that organizes the prefixes by prefix-id.

`/sp reset`
> Resets your prefix.

`/sp set <prefix-id>`
> Sets your prefix.

`/sp reload`
> Reloads the plugin.

# Configuration

```yml
saving-type: "file"
mysql:
  ip: "localhost:3306"
  name: prefixes
  user: username1
  pass: badpassword!

default-prefix: "<white>[<gray>Player</gray>]</white> "
prefix-menu-name: "<bold>Prefix Menu</bold>"

header:
  name: "<aqua>Click to Reset Your Prefix</aqua>"
  lore: []
  material: ENDER_EYE
  count: 1
  custom-model-data: null
```

## Configuration Settings

| Setting          | Description                                 | Valid Values              |
|------------------|---------------------------------------------|---------------------------|
| saving-type      | What type of saving system should this use? | `PDC`, `FILE`, `MYSQL`    |
| mysql            | MySQL Connection Settings                   | Valid SQL Connection Info |
| default-prefix   | Fallback prefix, supports Placeholders.     | String                    |
| prefix-menu-name | The name of the prefix menu.                | String                    |
| header           | Section for Header Item Configuration       | *See Below*               |

### Saving Types

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

### Header Configuration

The prefix menu header is configurable in multiple ways.

```yaml
header:
  name: "<aqua>Click to Reset Your Prefix</aqua>"
  lore: []
  material: ENDER_EYE
  count: 1
  custom-model-data: null
```

__Name__
> This is a String representing the item's name.
> 
> Defaults to "Click to Reset Your Prefix".
> 
> Mini-Message and Placeholders are supported.

__Lore__
> This is a list of Strings representing each lore line.
> 
> Defaults to an empty list.
> 
> Mini-Message and Placeholders are supported.

__Material__
> This is a String representing the Material Enumerated Type from [Paper Material Enum](https://jd.papermc.io/paper/1.20/org/bukkit/Material.html)
> 
> Invalid or missing values defaults to ENDER_EYE (Eye of Ender).

__Count__
> This is a String / Integer representing the item stack quantity.
> 
> Invalid or missing values defaults to 1. Do not set to 0.
> 
> Placeholders are supported, so long as they become an int representation.

__Custom Model Data__
> This is an integer that represents the custom model data.
> 
> Invalid or missing value leaves the item unchanged.

## Creating Prefixes

Prefixes are stored in the `prefixes.yml` file, this is the default.

```yml
#####
# Prefixes.yml
# ---
# For information on how to use this config, see the readme: https://github.com/ADHDMC/SimplePrefixes
#####
absorption:
  display-name: "<yellow>Absorption"
  description:
    - "<yellow><bold>FUN FACT:"
    - "<white>  This was a prefix I used during testing!"
    - "<white>  This also supports PlaceholderAPI!"
  prefix: "<white>[<yellow>Absorption: %player_absorption%</yellow>]</white>"
  verify-always: true
  requirements:
    - "compare_int %player_absorption% > 0"
permitted:
  display-name: "<green>The Permitted"
  description:
    - "<aqua>You do have permission to run this command!"
  prefix: "<white>[<green>Permitted</green>]</white>"
  requirements:
    - "permission simpleprefix.permitted"
undying:
  display-name: "<red>The Undying"
  description:
    - "<gray>Death hunts you..."
    - "<gray>You evade it..."
  prefix: "<white>[<red>Undying</red>]</white>"
  verify-always: true
  requirements:
    - "statistic DEATHS == 0"
wither_hunter:
  display-name: "<black>The Wither Hunter"
  description:
    - "Can you even read that text?"
  prefix: "<white>[<black>Wither Hunter</black>]</white>"
  show-when-locked: false
  requirements:
    - "advancement minecraft:nether/summon_wither"
custom_model_data:
  display-name: "<aqua>Bucket Mobs!</aqua>"
  description:
    - "If you have SimpleBucketMobs' resource pack"
    - "then this is an Enderman Bucket!"
  prefix: "<white>[<aqua>Simple Bucket Mobs</aqua>]</white>"
  item:
    material: BUCKET
    count: 2
    custom-model-data: 22000
```

Prefix ID **[Required]**
> This is the Prefix ID. Every prefix is uniquely identified by this value.
> 
> This does mean you cannot have two of the same Prefix IDs.

Display Name (`display-name`) **[Required]**
> This is the Display Name of the prefix. It is the formal name of the prefix.
> 
> Supports Placeholders from PlaceholderAPI.

Prefix (`prefix`) **[Required]**
> This is the String representation of the prefix that will be displayed in place of `%sp_prefix%` / `%sp_prefix_legacy%`.
>
> Supports Placeholders from PlaceholderAPI.

Description (`description`)
> This is a list of strings. Each string will be a new line in the description part of the GUI.
>
> Supports Placeholders from PlaceholderAPI.

Item (`item`)
> This is a way to define your item stacks.
> 
> - `material` defines the material of the item. Invalid or missing defaults to `NAME_TAG`. [Paper Material Enum](https://jd.papermc.io/paper/1.20/org/bukkit/Material.html)
> - `count` changes the stack size (the number of items in the stack). Invalid or missing defaults to `1`.
> - `custom-model-data` changes the custom model data, used for Resource Packs. Invalid or missing leaves the item unchanged.
> 
> If this section is missing, the itemstack is defaulted to a single name tag.

Show When Locked (`show-when-locked`)
> If this is true, the prefix will not show in the menu if the requirements are not met.
>
> This defaults to `true`.

Verify Always (`verify-always`)
> Checks the requirements each time the player's prefix is requested by PAPI.
> If the check fails, the player's prefix is cleared.
>
> This defaults to `false`.

### Minimum Configuration for a Prefix

```yml
# A prefix with the ID "example-prefix"...
example-prefix:
  display-name: "The Example Prefix"
  prefix: "<white>[<rainbow>Example</rainbow>]</white> "
```

### Prefix Requirements

Requirements are conditions that must be held true in order to equip the prefix.
These requirements come in multiple forms that will be explained here.

> **Note**
> 
> Requirements that are incorrectly formatted or produce errors are ignored.
> 
> Requirements that cannot guarantee failure will not be checked either.
> To check player permissions, the player has to be online,
> so while the player is offline, these checks are ignored.
> 
> `verify-always` will make the requirements check every time the player's prefix is requested.
> If the requirement check fails, the prefix saved is cleared.

<u>**Permission**</u>

Format: `permission <permission.node> [false]`

> 
> `<permission.node>` represents a permission the player may have.
> 
> `[false]` is optional and must be provided to invert the result.
> This means if the permission is `example.permission`, the player must NOT have this permission.
> 
> <u>Example</u>: `permission example.permission` (Player has permission example.permission)
> 
> <u>Example</u>: `permission example.permission false` (Player does not have permission example.permission)

<u>**Statistic**</u>

Format: `statistic <statistic> <operator> <value>`

>
> `<statistic>` is an enum value from [this page](https://jd.papermc.io/paper/1.19/org/bukkit/Statistic.html).
> This represents some statistic the player has.
>
> `<operator>` is a comparison operator.
> Valid operators are `>`, `<`, `>=`, `<=`, `==`, `!=`
>
> `<value>` is an integer and can be compared to the statistic.
>
> <u>Example</u>: `statistic ANIMALS_BRED >= 100` (Player has bred 100 or more animals).

<u>**Advancement**</u>

Format: `advancement <namespace:advancement> [false]`

> `<namespace:advancement>` is a Namespaced Key representing an Advancement.
> 
> You can find vanilla advancements on [this page](https://minecraft.fandom.com/wiki/Advancement).
> This represents some advancement the player can earn. Minecraft Advancements use the `minecraft` namespace.
> 
> `[false]` is optional and must be provided to invert the result.
> This means that if the advancement is `minecraft:nether/summon_wither`, the player must not have this advancement.
>
> <u>Example</u>: `advancement minecraft:nether/summon_wither` (Player has the Minecraft Summon Wither advancement).
>
> <u>Example</u>: `advancement minecraft:nether/summon_wither false` (Player does not have the Minecraft Summon Wither advancement).

<u>**Compare Int**</u>

Format: `compare_int <placeholder> <operator> <value>`

> `<placeholder>` is any PlaceholderAPI placeholder that can return a valid integer.
>
> `<operator>` is a comparison operator.
> Valid operators are `>`, `<`, `>=`, `<=`, `==`, `!=`
>
> `<value>` is an integer and can be compared to the placeholder.
>
> <u>Example</u>: `compare_int %player_absorption% > 0` (Player has absorption value greater than 0).

<u>**Unimplemented**</u>

- Compare String, Case Sensitive
- Compare String, Case Insensitive

## WIP Features

- Special Configurable Icons (ie: Player Heads)
- Compare String, Requirement Checks
- "Live Now" Livestreaming Implementation
