其实就是hibernate的增删查改操作。[使用示例可点此查看](https://github.com/xnx3/iw/tree/master/src/com/xnx3/j2ee/controller/admin)

# 方式一：原生SQL语句操作
#### 新增一条记录
````
sqlService.executeSql("INSERT INTO user VALUES ('管雷鸣', 25)");

````
#### 修改一条记录
````
sqlService.executeSql("UPDATE user SET age = 25 WHERE name = '管雷鸣'");
````

#### 删除一条记录
````
sqlService.executeSql("DELETE FROM user WHERE name = '管雷鸣'");
````

#### 查询记录
````
List<Map<String,Object>> list = sqlService.findMapBySqlQuery("SELECT * FROM user");
````



# 方式二：POJO对象操作

#### 新增一条记录
````
User user = new User();
user.setNickname("管雷鸣");
user.setEmail("mail@xnx3.com");
sqlService.save(user);
````

#### 查询一条记录
````
//将user数据表中，id为1的记录取出，返回User实体类
User user = sqlService.findById(User.class, 1);

````

#### 修改一条记录
````
User user = sqlService.findById(User.class, 1);
user.setNickname("管某人");
sqlService.save(user);
````

#### 删除一条记录
````
User user = sqlService.findById(User.class, 1);
sqlService.delete(user);
````


