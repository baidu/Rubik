package rubik.generate.aggregate.demo_com_mars_rubik_test_detail_java

import android.content.Context
import androidx.annotation.Keep
import com.mars.component.detail.java.api.Apis
import com.mars.component.detail.java.api.ApisAsyncReturn
import com.mars.component.detail.java.api.ApisForInstance
import com.mars.component.detail.java.api.callback.Callback
import com.mars.component.detail.java.api.callback.Callbackable
import com.mars.component.detail.java.test.TestJavaDataType
import com.mars.component.detail.java.ui.FirstPageActivity
import com.mars.component.detail.java.ui.SecondActivity
import com.rubik.annotations.source.RAggregate
import com.rubik.annotations.source.RGenerated
import com.rubik.context.Aggregatable
import com.rubik.context.AggregateFactory
import com.rubik.identity.RAggregateId
import com.rubik.route.mapping.castToTypeOfT
import com.rubik.route.mapping.mapToType
import com.rubik.route.mapping.toTypeOfT
import kotlin.Array
import kotlin.Function0
import kotlin.Int
import kotlin.Long
import kotlin.LongArray
import kotlin.String
import kotlin.Unit
import kotlin.collections.List
import rubik.generate.context.demo_com_mars_rubik_test_detail_java.DetailJavaRouteActions
import com.mars.component.detail.java.value.TestJavaBean as TestJavaBeanOrg
import com.rubik.activity.Launcher as RubikLauncher
import com.rubik.route.Queries as RubikQueries
import com.rubik.route.ResultGroups as RubikResultGroups
import com.rubik.route.Result as RubikResult
import com.rubik.router.uri.Path as RubikPath
import rubik.generate.context.demo_com_mars_rubik_test_detail_java.TestJavaBean as TestJavaBeanCtx

/**
 * aggregate router function and router event of Rubik Context.
 *
 * uri: [demo://com.mars.rubik-test.detail-java] 
 * version: 0.0.1-DEV
 */
@RGenerated(
  kind = "aggregate",
  by = "rubik-kapt:1.10.0.0-K1_5-LOCAL",
  version = "0.0.1-DEV"
)
@Keep
class DetailJavaAggregate : Aggregatable, DetailJavaRouteActions {
  override fun onEvent(msg: String, queries: RubikQueries) {
    when (msg){
      else -> { }
    }
  }

  override fun onRoute(
    path: String,
    queries: RubikQueries,
    results: RubikResultGroups
  ) {
    when {
      "doTestDataType1" == path ->  {
        val data = queries.value(0, null)
        doTestDataType1(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType1-2" == path ->  {
        val data = queries.value(0, null)
        doTestDataType12(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType2" == path ->  {
        val data = queries.value(0, null)
        doTestDataType2(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType2-2" == path ->  {
        val data = queries.value(0, null)
        doTestDataType22(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType3" == path ->  {
        val data = queries.value(0, null)
        doTestDataType3(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType4" == path ->  {
        val data = queries.value(0, null)
        doTestDataType4(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType5" == path ->  {
        val data = queries.value(0, null)
        doTestDataType5(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType6" == path ->  {
        val data = queries.value(0, null)
        doTestDataType6(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType7" == path ->  {
        val data = queries.value(0, null)
        doTestDataType7(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "do-sth-bean-provide-instance-by-func" == path ->  {
        val rubikInstanceValue = queries.value(0, null)
        val d1 = queries.value(1, null)
        val routeResultTransformer: (TestJavaBeanCtx) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        doSthBeanProvideInstanceByFunc(rubikInstanceValue.castToTypeOfT(), d1.castToTypeOfT(), routeResultTransformer)
      }
      "doSth" == path ->  {
        doSth()
      }
      "1.0/property/property" == path ->  {
        val routeResultTransformer: (String) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        propertyProperty(routeResultTransformer)
      }
      "doSthStatic" == path ->  {
        val i = queries.value(0, null)
        val j = queries.value(1, null)
        val k = queries.value(2, null)
        val routeResultTransformer: (String) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        doSthStatic(i.castToTypeOfT(), j.castToTypeOfT(), k.castToTypeOfT(), routeResultTransformer)
      }
      "property/static" == path ->  {
        val routeResultTransformer: (String) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        propertyStatic(routeResultTransformer)
      }
      "do-sth-async-open" == path ->  {
        val onCallCallback: (String?, Int) -> Unit = { lambdaArg0, lambdaArg1 ->
          results.set(0, RubikResult(lambdaArg0),RubikResult(lambdaArg1))
        }
        doSthAsyncOpen(onCallCallback)
      }
      else -> { onRouteExt1(path, queries, results) }
    }
  }

  private fun onRouteExt1(
    path: String,
    queries: RubikQueries,
    results: RubikResultGroups
  ) {
    when {
      "do-sth-async-interface" == path ->  {
        val onCallCallback: (String?, Int) -> Unit = { lambdaArg0, lambdaArg1 ->
          results.set(0, RubikResult(lambdaArg0),RubikResult(lambdaArg1))
        }
        doSthAsyncInterface(onCallCallback)
      }
      "activity/java_page1" == path ->  {
        // com.mars.component.detail.java.ui.FirstPageActivity
        // - parameters:
        // --- key_str4 : kotlin.collections.List<kotlin.String>?
        // --- key_str5 : kotlin.collections.List<com.mars.component.detail.java.value.TestJavaBean>?
        // --- key_str6 : kotlin.Array<com.mars.component.detail.java.value.TestJavaBean>?
        RubikLauncher().launch(FirstPageActivity::class.java, queries, null, results)
      }
      RubikPath("activity/java_page2/{key_str1}/{key_str2}/{key_str3}").matching(path) ->  {
        // com.mars.component.detail.java.ui.SecondActivity
        // - parameters:
        // --- key_str1 : kotlin.String?
        // --- key_str2 : kotlin.String?
        // --- key_str3 : kotlin.Int?
        RubikLauncher().launch(SecondActivity::class.java, queries, RubikPath("activity/java_page2/{key_str1}/{key_str2}/{key_str3}").getParameters(path), results)
      }
      else -> { throw com.rubik.route.exception.BadPathOrVersionException(path) }
    }
  }

  override fun doTestDataType1(data: Context): Context? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType1
    // - parameters:
    // --- data : android.content.Context
    // - resultType:
    // --- android.content.Context
    return TestJavaDataType().doTestDataType1(
      data /* -> NO NEED TO MAPPING :[android.content.Context] */ 
    ) /* -> NO NEED TO MAPPING :[android.content.Context] */ 
  }

  override fun doTestDataType12(data: Context?): Context? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType1_2
    // - parameters:
    // --- data : android.content.Context?
    // - resultType:
    // --- android.content.Context?
    return TestJavaDataType().doTestDataType1_2(
      data /* -> NO NEED TO MAPPING :[android.content.Context?] */ 
    ) /* -> NO NEED TO MAPPING :[android.content.Context?] */ 
  }

  override fun doTestDataType2(data: Int?): Int? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType2
    // - parameters:
    // --- data : kotlin.Int?
    // - resultType:
    // --- kotlin.Int?
    return TestJavaDataType().doTestDataType2(
      data /* -> NO NEED TO MAPPING :[kotlin.Int?] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.Int?] */ 
  }

  override fun doTestDataType22(data: Int): Int? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType2_2
    // - parameters:
    // --- data : kotlin.Int
    // - resultType:
    // --- kotlin.Int
    return TestJavaDataType().doTestDataType2_2(
      data /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
  }

  override fun doTestDataType3(data: String?): String? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType3
    // - parameters:
    // --- data : kotlin.String?
    // - resultType:
    // --- kotlin.String?
    return TestJavaDataType().doTestDataType3(
      data /* -> NO NEED TO MAPPING :[kotlin.String?] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.String?] */ 
  }

  override fun doTestDataType4(data: TestJavaBeanCtx?): TestJavaBeanCtx? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType4
    // - parameters:
    // --- data : com.mars.component.detail.java.value.TestJavaBean?
    // - resultType:
    // --- com.mars.component.detail.java.value.TestJavaBean?
    return TestJavaDataType().doTestDataType4(
      data?.toTypeOfT() /* -> TO ORIGINAL TYPE :[com.mars.component.detail.java.value.TestJavaBean?] */ 
    )?.toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail_java.TestJavaBean?] */ 
  }

  override fun doTestDataType5(data: List<String>?): List<String>? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType5
    // - parameters:
    // --- data : kotlin.collections.List<kotlin.String>?
    // - resultType:
    // --- kotlin.collections.List<kotlin.String>?
    return TestJavaDataType().doTestDataType5(
      data /* -> NO NEED TO MAPPING :[kotlin.collections.List<kotlin.String>?] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.collections.List<kotlin.String>?] */ 
  }

  override fun doTestDataType6(data: Array<Long>?): Array<Long>? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType6
    // - parameters:
    // --- data : kotlin.Array<kotlin.Long>?
    // - resultType:
    // --- kotlin.Array<kotlin.Long>?
    return TestJavaDataType().doTestDataType6(
      data /* -> NO NEED TO MAPPING :[kotlin.Array<kotlin.Long>?] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.Array<kotlin.Long>?] */ 
  }

  override fun doTestDataType7(data: LongArray?): LongArray? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType7
    // - parameters:
    // --- data : kotlin.LongArray?
    // - resultType:
    // --- kotlin.LongArray?
    return TestJavaDataType().doTestDataType7(
      data /* -> NO NEED TO MAPPING :[kotlin.LongArray?] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.LongArray?] */ 
  }

  override fun doSthBeanProvideInstanceByFunc(
    rubikInstanceValue: String,
    d1: TestJavaBeanCtx,
    routeResultTransformer: (TestJavaBeanCtx) -> Unit
  ) {
    // com.mars.component.detail.java.api.ApisForInstance.doSthBean
    // - parameters:
    // --- d1 : com.mars.component.detail.java.value.TestJavaBean
    // - resultType:
    // --- com.mars.component.detail.java.value.TestJavaBean
    routeResultTransformer(ApisForInstance(
      rubikInstanceValue /* -> NO NEED TO MAPPING :[kotlin.String] */ 
    ) /* -> NO NEED TO MAPPING :[com.mars.component.detail.java.api.ApisForInstance] */ .doSthBean(
      d1.toTypeOfT() /* -> TO ORIGINAL TYPE :[com.mars.component.detail.java.value.TestJavaBean] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail_java.TestJavaBean] */ )
  }

  override fun doSth() {
    // com.mars.component.detail.java.api.Apis.doSth
    // - resultType:
    // --- null
    Apis().doSth()
  }

  override fun propertyProperty(routeResultTransformer: (String) -> Unit) {
    // com.mars.component.detail.java.api.Apis.property
    // - resultType:
    // --- kotlin.String
    routeResultTransformer(Apis().property /* -> NO NEED TO MAPPING :[kotlin.String] */ )
  }

  override fun doSthStatic(
    i: Int,
    j: String,
    k: Long,
    routeResultTransformer: (String) -> Unit
  ) {
    // com.mars.component.detail.java.api.Apis.doSthCompanion
    // - parameters:
    // --- i : kotlin.Int
    // --- j : kotlin.String
    // --- k : kotlin.Long
    // - resultType:
    // --- kotlin.String
    routeResultTransformer(com.mars.component.detail.java.api.Apis.doSthCompanion(
      i /* -> NO NEED TO MAPPING :[kotlin.Int] */ ,
      j /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      k /* -> NO NEED TO MAPPING :[kotlin.Long] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.String] */ )
  }

  override fun propertyStatic(routeResultTransformer: (String) -> Unit) {
    // com.mars.component.detail.java.api.Apis.propertyCompanion
    // - resultType:
    // --- kotlin.String
    routeResultTransformer(com.mars.component.detail.java.api.Apis.propertyCompanion /* -> NO NEED TO MAPPING :[kotlin.String] */ )
  }

  override fun doSthAsyncOpen(onCallCallback: (String?, Int) -> Unit) {
    // com.mars.component.detail.java.api.ApisAsyncReturn.doSthAsyncOpen
    // - parameters:
    // --- onResult : com.mars.component.detail.java.api.callback.Callback
    // - resultType:
    // --- null
    val onResultCallbackTransformer = object : Callback() {
      override fun onCall(v1: String?, v2: Int) {
        onCallCallback(
          v1 /* -> NO NEED TO MAPPING :[kotlin.String?] */ ,
          v2 /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
        )}
    }
    ApisAsyncReturn().doSthAsyncOpen(
      onResultCallbackTransformer
    )
  }

  override fun doSthAsyncInterface(onCallCallback: (String?, Int) -> Unit) {
    // com.mars.component.detail.java.api.ApisAsyncReturn.doSthAsyncInterface
    // - parameters:
    // --- onResult : com.mars.component.detail.java.api.callback.Callbackable
    // - resultType:
    // --- null
    val onResultCallbackTransformer = object : Callbackable {
      override fun onCall(v1: String?, v2: Int) {
        onCallCallback(
          v1 /* -> NO NEED TO MAPPING :[kotlin.String?] */ ,
          v2 /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
        )}
    }
    ApisAsyncReturn().doSthAsyncInterface(
      onResultCallbackTransformer
    )
  }

  @RGenerated(
    kind = "aggregate_companion",
    by = "rubik-kapt:1.10.0.0-K1_5-LOCAL",
    version = "0.0.1-DEV"
  )
  @Keep
  companion object : AggregateFactory() {
    override val URI: String = "demo://com.mars.rubik-test.detail-java"

    override val EVENT_MSGS: List<String> = listOf()

    override val CREATOR: Function0<Aggregatable> = {DetailJavaAggregate()}
  }
}

/**
 * generated Rubik AggregateId.
 *
 * uri: [demo://com.mars.rubik-test.detail-java] 
 * originalToken: 
 * [API]do-sth-async-interface|N|F|F|F|[IVO]doSthAsyncInterface|F|N|[QUY]onResult|com.mars.component.detail.java.api.callback.Callbackable|F|F|T[<]|N|com.mars.component.detail.java.api.ApisAsyncReturn|F[<][<]
 * [API]do-sth-async-open|N|F|F|F|[IVO]doSthAsyncOpen|F|N|[QUY]onResult|com.mars.component.detail.java.api.callback.Callback|F|F|T[<]|N|com.mars.component.detail.java.api.ApisAsyncReturn|F[<][<]
 * [API]do-sth-bean-provide-instance-by-func|N|F|F|F|[IVO]doSthBean|F|[AIN]do-sth-bean-provide-instance-by-func|[IVO]com.mars.component.detail.java.api.ApisForInstance|F|N|[QUY]rubikInstanceValue|String|F|F|F[<]|[RST]B|com.mars.component.detail.java.api.ApisForInstance[<]|com.mars.component.detail.java.api.ApisForInstance|F[<][<]|[QUY]rubikInstanceValue|String|F|F|F[<]|[QUY]d1|TestJavaBean|F|F|F[<]|[RST]B|TestJavaBean[<]|com.mars.component.detail.java.api.ApisForInstance|F[<][<]
 * [API]doSth|N|F|F|F|[IVO]doSth|F|N|E|N|com.mars.component.detail.java.api.Apis|F[<][<]
 * [API]doSthStatic|N|F|F|F|[IVO]doSthCompanion|T|N|[QUY]i|Int|F|F|F[<]|[QUY]j|String|F|F|F[<]|[QUY]k|kotlin.Long|F|F|F[<]|[RST]B|String[<]|com.mars.component.detail.java.api.Apis|F[<][<]
 * [API]doTestDataType1|N|F|T|F|[IVO]doTestDataType1|F|N|[QUY]data|android.content.Context|F|F|F[<]|[RST]B|android.content.Context[<]|TestJavaDataType|F[<][<]
 * [API]doTestDataType1-2|N|F|T|F|[IVO]doTestDataType1_2|F|N|[QUY]data|android.content.Context?|F|F|F[<]|[RST]B|android.content.Context?[<]|TestJavaDataType|F[<][<]
 * [API]doTestDataType2|N|F|T|F|[IVO]doTestDataType2|F|N|[QUY]data|Int?|F|F|F[<]|[RST]B|Int?[<]|TestJavaDataType|F[<][<]
 * [API]doTestDataType2-2|N|F|T|F|[IVO]doTestDataType2_2|F|N|[QUY]data|Int|F|F|F[<]|[RST]B|Int[<]|TestJavaDataType|F[<][<]
 * [API]doTestDataType3|N|F|T|F|[IVO]doTestDataType3|F|N|[QUY]data|String?|F|F|F[<]|[RST]B|String?[<]|TestJavaDataType|F[<][<]
 * [API]doTestDataType4|N|F|T|F|[IVO]doTestDataType4|F|N|[QUY]data|TestJavaBean?|F|F|F[<]|[RST]B|TestJavaBean?[<]|TestJavaDataType|F[<][<]
 * [API]doTestDataType5|N|F|T|F|[IVO]doTestDataType5|F|N|[QUY]data|kotlin.collections.List<String>?|F|F|F[<]|[RST]B|kotlin.collections.List<String>?[<]|TestJavaDataType|F[<][<]
 * [API]doTestDataType6|N|F|T|F|[IVO]doTestDataType6|F|N|[QUY]data|kotlin.Array<kotlin.Long>?|F|F|F[<]|[RST]B|kotlin.Array<kotlin.Long>?[<]|TestJavaDataType|F[<][<]
 * [API]doTestDataType7|N|F|T|F|[IVO]doTestDataType7|F|N|[QUY]data|kotlin.LongArray?|F|F|F[<]|[RST]B|kotlin.LongArray?[<]|TestJavaDataType|F[<][<]
 * [API]1.0/property/property|N|F|F|F|[IVO]property|F|N|E|[RST]B|String[<]|com.mars.component.detail.java.api.Apis|F[<][<]
 * [API]property/static|N|F|F|F|[IVO]propertyCompanion|T|N|E|[RST]B|String[<]|com.mars.component.detail.java.api.Apis|F[<][<]
 * [ACT]activity/java_page1|B|F|T|com.mars.component.detail.java.ui.FirstPageActivity|[PRT]key_str4|serializable|kotlin.collections.List<String>?[<]|[PRT]key_str5|value|kotlin.collections.List<TestJavaBean>?[<]|[PRT]key_str6|value|kotlin.Array<TestJavaBean>?[<][<]
 * [ACT]activity/java_page2/{key_str1}/{key_str2}/{key_str3}|B|F|T|com.mars.component.detail.java.ui.SecondActivity|[PRT]key_str1|N|String?[<]|[PRT]key_str2|N|String?[<]|[PRT]key_str3|N|Int?[<][<]
 * [VLE]TestJavaBean|E|E|[FLD]Int|[ANT]com.google.gson.annotations.SerializedName|value = "ddd111"[<]|N|F[<]|[FLD]String?|[ANT]com.google.gson.annotations.SerializedName|value = "ddd222"[<]|[ANT]androidx.annotation.Nullable|E[<]|N|F[<][<]
 * [MAP]Int->kotlin.Int[<]
 * [MAP]String->kotlin.String[<]
 * [MAP]TestJavaBean->com.mars.component.detail.java.value.TestJavaBean[<]
 * [MAP]TestJavaDataType->com.mars.component.detail.java.test.TestJavaDataType[<] 
 * version: 0.0.1-DEV
 */
@RGenerated(
  kind = "aggregate_Id",
  by = "rubik-kapt:1.10.0.0-K1_5-LOCAL",
  version = "0.0.1-DEV"
)
@RAggregate(
  uri = "demo://com.mars.rubik-test.detail-java",
  version = "0.0.1-DEV",
  token = "7c5174bb8aaea699e6ab6eec60230bf7"
)
@Keep
class DetailJavaAggregateId :
    RAggregateId(uri = "demo://com.mars.rubik-test.detail-java", version = "0.0.1-DEV", token = "7c5174bb8aaea699e6ab6eec60230bf7")
