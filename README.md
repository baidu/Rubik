![rubik-logo-001](https://user-images.githubusercontent.com/7745189/174275733-ff1ec56e-82ea-4c3b-86de-b2b07d258842.jpeg)
# Rubik
Rubik是一套解决Android平台组件化的综合方案，提供gradle project之间的路由通讯能力，以及对gradle project的组件定义、版本控制、maven发布、aar/jar与源码之间的切换以及组件的自由组合等能力。

Rubik由两部分组成：
* Rubik Router ：即Rubik的函数级路由能力，与一般的页面路由不同，Rubik Router允许把Uri及参数，导航到工程内部，任意的一个公开的JVM语言（Java/Kotlin）函数的执行上，以便于更灵活的进行gradle project之间不基于代码调用的通讯。
* Rubik 工具链 ：提供组件上下文的定义、版本控制、maven发布、aar/jar与源码之间的切换等能力，包括4个gradle plugin:
    + rubik：
        - 提供全局定义组件的能力，并根据全局定义自动启用rubik-context、rubik-root等插件
    + rubik-context：
        - 提供task，自动生成镜像函数等中间代码，并把中间代码打包成context.jar ,按版本号发布到maven
        - 提供task，把业务代码按flavor、版本号编译成aar (包括代码、资源、内置SDK)发布到maven
        - 通过全局定义的组件，为组件所在子工程自动添加其他context.jar的依赖
    + rubik-root：
        - 给壳工程提供筛选组件等能力，根据flavor、版本号筛选要打包进apk的业务组件
        - 提供组件的源码工程和aar切换的能力
    + rubik-test:
        - 给工程提供单元测试环境
        
## 快速开始
1. 创建或使用已有的android application project，作为"壳工程"（如demo代码中的demo_root_app），用于把组件组装成Apk。
2. 创建或使用已有的一个或多个android library project，作为"组件工程"（如demo代码中的demo_component_detail、demo_component_home等），用于开发真正的业务逻辑。
3. 为最外层gradle project添加apply plugin: 'rubik'，启用rubik插件。
4. 在最外层gradle project的build.gradle文件或同级目录下的rubik-*.gradle文件中，配置组件信息：
```
rubik {
    component { // 第一个组件
        uri "app://com.myapp.home"  // 组件的Uri
        dependencies {    // 组件需要依赖的其他组件
            uri ("app://com.myapp.detail" ) { 
                version "0.1.1"  // 依赖其他组件的版本信息
            }
            uri( … ) 
        }
        source {    // 定义默认来源，如不需切换源码和aar，可以只声明project或maven
            project (":demo_component_home") 
        }
    }
    component { … }  //第二个组件
} 
```
5. 在一个组件工程内，通过注解定义路由路径，作为组件暴露给其他组件的接口：
    
通过RRoute注解声明函数路由:
```
@RRoute(path = "account/user") 
fun getUser(id : Int, name : String) : User? { 
    …
}
```
6. 在另一个组件工程内，调用其他组件的路由接口：
   
通过Kotlin DSL：
```
navigate {
    uri = "app://com.myapp.detail/account/user"
    query { // 请求的参数
        "id" with 400
        "name" with "CuiVincent" 
    }
    result<User?> { user -> 
    // 通过泛型指定接收数据类型，多次异步返回时，可以用多个result接收
        …
    }
} 
```
   

通过自动生成的镜像函数：
```
DetailContext.Account.user(400, "CuiVincent" ) { user ->
    …
}
```
7. 在“壳工程”的的build.gradle文件或同级目录下的rubik-*.gradle文件中，指定壳工程最终要将哪些组件，以哪种方式引入，并打包到最终的编译产物之中：
```
rubik {	
    packing {
        projectMode { // projectMode，通过源码工程的方式引入组件
            uri ("app://com.myapp.home")
            uri ("app://com.myapp.detail")
        }
        mavenMode { // mavenMode，通过maven上的aar的方式引入组件
            uri ("app://com.myapp.other") {
                version "0.2.0" 
            }
        }
    }
} 
```
## 测试
* 通过rubik-test插件，给当前工程的androidTest variant添加全部可pick组件的context.jar依赖，便于写测试用例。
```
@RunWith(AndroidJUnit4::class)
class RouterTestCase {
    @Before
    fun init() {
        Rubik.init()
    } // 初始化Rubik
    @Test
    fun usePerview() {
        PerviewContext.preViewVideo(path : String) { success ->
            log("preViewVideo success:${success}")
        } // 测试用例
    }
    … // 继续写测试用例
 }

```

## 如何贡献
请用Kotlin语言编写，所有注明动机的合理改动提交都会被接收。


## 讨论
百度如流讨论群：5425804
