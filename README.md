# Fahrenheit
A simple, light-weight and efficient solution to add competitiveness to any type of server based on permissions!

Players will only need to do /rankup and they will be charged for every rank they reach, each one with your own custom rewards and cost, so the possibilities are endless!

Each rank has its own rewards, cost, name, and tag, each of them configurable in the ranks.yml file.

It doesn't need any database, as it will store the ranks in the player's permission list, and the configuration of it is very straightforward.

### Placeholders:
* %fahrenheit_rank% - Displays the Rank of the player
* %fahrenheit_rank_tag% - Displays a friendly Rank tag which can be used as prefix with a PlaceholderAPI compatible plugin
* %fahrenheit_rank_cost_(rank id as in ranks.yml)% - Displays the cost of the specified Rank

### Dependencies:
* Spigot 1.8 or higher
* Vault
* Vault-compatible permission plugin (like LuckPerms)
* Vault-compatible economy plugin (like EssentialsX)
* PlaceholderAPI

### API Usage:
To access the API, you will get the instance from the bukkit ServicesManager:
```java
ServicesManager manager = plugin#getServer().getServicesManager();
RegisteredServiceProvider<FahrenheitApi> rspPe = manager.getRegistration(FahrenheitApi.class);
if (rspPe == null) {
    System.out.println("FahrenheitAPI not found");
    return;
}
FahrenheitApi api = rspPe.getProvider();
```

You can see all the methods of the API [here](https://github.com/AguaDeLaMiseria/fahrenheit/blob/main/src/main/java/com/aguadelamiseria/fahrenheit/api/FahrenheitApi.java).

There is also a [PlayerRankupEvent](https://github.com/AguaDeLaMiseria/fahrenheit/blob/main/src/main/java/com/aguadelamiseria/fahrenheit/api/events/PlayerRankupEvent.java).
