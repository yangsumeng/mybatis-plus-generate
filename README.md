### 实现功能
-[x] Java自动生成增删改查接口
-[x] js自动生成访问
-[x] SQL自动生成菜单
-[x] SQL自动生成菜单权限
-[x] Vue生成查询列表
-[x] Vue生成权限控制
-[x] Vue生成权限控制
-[x] Vue新增删除修改查询
-[x] 将_name 和 _code的字段自动生成查询条件
-[x] 增加前端页面IMSCodeGenerator例子  可以生成页面select是根据数据库comment自动生成字典
        comment EG:  是否转发:1|是，2|否       

### 使用説明
- 判断字段类型
```
if(${field.propertyType.equals("String")})
```
- 判断是否是基础字段   
```
    - ${field.baseField}  ##id state create_by  update_by update_time create_time
```
- 页面自动生成的字段
```$xslt
#foreach($field in ${table.fields})
    #if(${field.name.endsWith("_name") || ${field.name.endsWith("_code"))
    #end
#end
```
- 不转义代码
```$xslt
#[[  $$$$$$$$$$$  ]]#
```
- 表test_sys_user转换后sysUser
```$xslt
${table.entityPath}.${codeField.propertyName}
```
- 表test_sys_user转换后SysUser
```$xslt
add${table.originalEntityName}
```
- 表test_sys_user.user_name转换后userName
```$xslt
${field.propertyName}
```
- 表test_sys_user.user_name转换后UserName
```$xslt
{field.capitalName}
```


