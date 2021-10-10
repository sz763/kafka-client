# kafka-client

Simple kafka client for monitoring topic values.

## How to start

<code>$ java -jar kafka-client-1.0.jar</code>

## How to use

- specify kafka hosts in config.yml like <code>host:port,host2:port2</code>
- start java app
- select topic in topic list
- click twice at topic name in topic list

## Actions

### Filering topics

- for filtering topics - click to topic list and press <code>ctrl + f</code>
- to reset filter - press <code>ctrl + f</code> and remove filtering value

### Filering events

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
