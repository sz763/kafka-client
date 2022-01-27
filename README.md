# kafka-client

Simple kafka client for monitoring topic values in real time, without pressing any refresh buttons.

<img src="https://user-images.githubusercontent.com/5479948/149766027-119ab947-b72e-4a8b-ac9d-8f5f3572a6e7.png" width="610" height="414">


## How to start

`$ java -jar kafka-client-${version}.jar` or just run `run.bat` if you are win user.

## How to use

- specify kafka `hosts` in config.yml like `host:port,host2:port2`
- start java app
- select topic in topic list
- click twice on the topic name in topic list
- specify `kafka -> application.id` and  `kafka -> group.id` in `config.yml` or these properties will be generated `kc_${UUID.randomUUID()`

## Actions

### Filtering topics

- for filtering topics - click to topic list and press <code>ctrl + f</code>
- to reset filter - press <code>ctrl + f</code> and remove filtering value

### Filtering events

#### Filter by event key

- for filtering event by key - click to table and press <code>ctrl + shfit + f</code>
- to reset filter - press <code>ctrl + f</code> and remove filtering value

#### Filter by event value

- for filtering event by value - click to table and press <code>ctrl + f</code>
- to reset filter - press <code>ctrl + f</code> and remove filtering value

### Copy event key/value

- Copy by right click: to copy value or key, click by right mouse button and select 'Copy'
- Copy by middle click: to copy value or key, click by middle button

### Formatted preview of key or value

- To preview key or value - click twice at key or value
