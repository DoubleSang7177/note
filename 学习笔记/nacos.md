当nacos以集群模式启动时,方法内的chooseLookup 方法,默认会检查nacos/conf目录下是否存在 cluster.conf文件,如果不存在,返回LookupType.ADDRESS_SERVER,如果存在, 返回LookupType.FILE_CONFIG
根据LookupType ,LookupFactory.createLookUp方法返回FileConfigMemberLookup或 AddressServerMemberLookup
这会在 ServerMemberManager 中,调用 实例的start() 方法
因此,当cnacos未找到cluster.conf,且是以集群模式(默认)启动时,会报错误
当然,根据源码,也可以在application.properties中配置 nacos.core.member.lookup.type=file

或者`startup.cmd -m standalone`也行（在bin在打开cmd)


