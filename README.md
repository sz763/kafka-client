# kafka-client

Simple kafka client for monitoring topic values.

![main_window](https://user-images.githubusercontent.com/5479948/136686116-e49a3249-7f31-4f08-be06-0e010e578c03.png)
![view](https://user-images.githubusercontent.com/5479948/136686118-3c34660c-7628-4d55-9415-11cdd42320cf.png)
![preview](https://user-images.githubusercontent.com/5479948/136686119-436d6863-5dea-4fc0-bee1-44bbef42c5de.png)


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
