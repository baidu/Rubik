package rubik.generate.aggregate.demo_com_mars_rubik_test_detail_java

import androidx.annotation.Keep
import com.rubik.activity.Launcher
import com.rubik.annotations.source.RGenerated
import com.rubik.context.Aggregatable
import com.rubik.context.AggregateFactory
import com.rubik.route.Queries
import com.rubik.route.Result
import com.rubik.route.Results
import com.rubik.route.set
import com.rubik.router.uri.Path
import kotlin.Function0
import kotlin.String
import kotlin.collections.List

/**
 * aggregate router function and router event of Rubik Context.
 *
 * context uri: [demo://com.mars.rubik-test.detail-java]
 * version: 0.1.3
 */
@RGenerated(
  kind = "aggregate",
  by = "rubik-kapt:1.9.0.1.T-K1_3-LOCAL",
  version = "0.1.3"
)
@Keep
class DetailJavaAggregate : Aggregatable {
  override fun onEvent(msg: String, queries: Queries) {
    when(msg){
      else -> {}
    }
  }

  override fun onRoute(
    path: String,
    queries: Queries,
    results: List<Results>
  ) {
    when {
      "doTestDataType1" == path ->  {
        // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType1
        // - parameters:
        // --- data : android.content.Context
        // - resultType:
        // --- android.content.Context
        val data = queries.toTypeOfT<android.content.Context>(0, null)
        com.mars.component.detail.java.test.TestJavaDataType().doTestDataType1(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType1-2" == path ->  {
        // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType1_2
        // - parameters:
        // --- data : android.content.Context?
        // - resultType:
        // --- android.content.Context?
        val data = queries.toTypeOfT<android.content.Context?>(0, null)
        com.mars.component.detail.java.test.TestJavaDataType().doTestDataType1_2(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType2" == path ->  {
        // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType2
        // - parameters:
        // --- data : kotlin.Int?
        // - resultType:
        // --- kotlin.Int?
        val data = queries.toTypeOfT<kotlin.Int?>(0, null)
        com.mars.component.detail.java.test.TestJavaDataType().doTestDataType2(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType2-2" == path ->  {
        // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType2_2
        // - parameters:
        // --- data : kotlin.Int
        // - resultType:
        // --- kotlin.Int
        val data = queries.toTypeOfT<kotlin.Int>(0, null)
        com.mars.component.detail.java.test.TestJavaDataType().doTestDataType2_2(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType3" == path ->  {
        // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType3
        // - parameters:
        // --- data : kotlin.String?
        // - resultType:
        // --- kotlin.String?
        val data = queries.toTypeOfT<kotlin.String?>(0, null)
        com.mars.component.detail.java.test.TestJavaDataType().doTestDataType3(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType4" == path ->  {
        // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType4
        // - parameters:
        // --- data : com.mars.component.detail.java.value.TestJavaBean?
        // - resultType:
        // --- com.mars.component.detail.java.value.TestJavaBean?
        val data = queries.toTypeOfT<com.mars.component.detail.java.value.TestJavaBean?>(0, null, true)
        com.mars.component.detail.java.test.TestJavaDataType().doTestDataType4(data).apply {
          results.set(0, Result(this, true))
        }
      }
      "doTestDataType5" == path ->  {
        // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType5
        // - parameters:
        // --- data : kotlin.collections.List<kotlin.String>?
        // - resultType:
        // --- kotlin.collections.List<kotlin.String>?
        val data = queries.toTypeOfT<kotlin.collections.List<kotlin.String>?>(0, null)
        com.mars.component.detail.java.test.TestJavaDataType().doTestDataType5(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType6" == path ->  {
        // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType6
        // - parameters:
        // --- data : kotlin.Array<kotlin.Long>?
        // - resultType:
        // --- kotlin.Array<kotlin.Long>?
        val data = queries.toTypeOfT<kotlin.Array<kotlin.Long>?>(0, null)
        com.mars.component.detail.java.test.TestJavaDataType().doTestDataType6(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType7" == path ->  {
        // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType7
        // - parameters:
        // --- data : kotlin.LongArray?
        // - resultType:
        // --- kotlin.LongArray?
        val data = queries.toTypeOfT<kotlin.LongArray?>(0, null)
        com.mars.component.detail.java.test.TestJavaDataType().doTestDataType7(data).apply {
          results.set(0, Result(this))
        }
      }
      "do-sth-bean-provide-instance-by-func" == path ->  {
        // com.mars.component.detail.java.api.ApisForInstance.doSthBean
        // - parameters:
        // --- d1 : com.mars.component.detail.java.value.TestJavaBean
        // - resultType:
        // --- com.mars.component.detail.java.value.TestJavaBean
        val rubikAssistValue = queries.toTypeOfT<kotlin.String>(0, null)
        val d1 = queries.toTypeOfT<com.mars.component.detail.java.value.TestJavaBean>(1, null, true)
        com.mars.component.detail.java.api.ApisForInstance(rubikAssistValue).doSthBean(d1).apply {
          results.set(0, Result(this, true))
        }
      }
      "doSth" == path ->  {
        // com.mars.component.detail.java.api.Apis.doSth
        // - resultType:
        // --- null
        com.mars.component.detail.java.api.Apis().doSth().apply {
          results.set(0, Result(this))
        }
      }
      "1.0/property/property" == path ->  {
        // com.mars.component.detail.java.api.Apis.property
        // - resultType:
        // --- kotlin.String
        com.mars.component.detail.java.api.Apis().property.apply {
          results.set(0, Result(this))
        }
      }
      "doSthStatic" == path ->  {
        // com.mars.component.detail.java.api.Apis.doSthCompanion
        // - parameters:
        // --- i : kotlin.Int
        // --- j : kotlin.String
        // --- k : kotlin.Long
        // - resultType:
        // --- kotlin.String
        val i = queries.toTypeOfT<kotlin.Int>(0, null)
        val j = queries.toTypeOfT<kotlin.String>(1, null)
        val k = queries.toTypeOfT<kotlin.Long>(2, null)
        com.mars.component.detail.java.api.Apis.doSthCompanion(i,j,k).apply {
          results.set(0, Result(this))
        }
      }
      "property/static" == path ->  {
        // com.mars.component.detail.java.api.Apis.propertyCompanion
        // - resultType:
        // --- kotlin.String
        com.mars.component.detail.java.api.Apis.propertyCompanion.apply {
          results.set(0, Result(this))
        }
      }
      "do-sth-async-open" == path ->  {
        // com.mars.component.detail.java.api.ApisAsyncReturn.doSthAsyncOpen
        // - parameters:
        // --- onResult : com.mars.component.detail.java.api.callback.Callback
        // - resultType:
        // --- null
        val onResult = object : com.mars.component.detail.java.api.callback.Callback() {
          override fun onCall(v1: kotlin.String?,v2: kotlin.Int) {
            results.set(0, Result(v1),Result(v2))
          }
        }
        com.mars.component.detail.java.api.ApisAsyncReturn().doSthAsyncOpen(onResult)
      }
      "do-sth-async-interface" == path ->  {
        // com.mars.component.detail.java.api.ApisAsyncReturn.doSthAsyncInterface
        // - parameters:
        // --- onResult : com.mars.component.detail.java.api.callback.Callbackable
        // - resultType:
        // --- null
        val onResult = object : com.mars.component.detail.java.api.callback.Callbackable {
          override fun onCall(v1: kotlin.String?,v2: kotlin.Int) {
            results.set(0, Result(v1),Result(v2))
          }
        }
        com.mars.component.detail.java.api.ApisAsyncReturn().doSthAsyncInterface(onResult)
      }
      "activity/java_page1" == path ->  {
        // com.mars.component.detail.java.ui.FirstPageActivity
        // - parameters:
        // --- key_str4 : kotlin.collections.List<kotlin.String>?
        // --- key_str5 : kotlin.collections.List<com.mars.component.detail.java.value.TestJavaBean>?
        // --- key_str6 : kotlin.Array<com.mars.component.detail.java.value.TestJavaBean>?
        Launcher().launch(com.mars.component.detail.java.ui.FirstPageActivity::class.java,queries,null,results)
      }
      Path("activity/java_page2/{key_str1}/{key_str2}/{key_str3}").matching(path) ->  {
        // com.mars.component.detail.java.ui.SecondActivity
        // - parameters:
        // --- key_str1 : kotlin.String?
        // --- key_str2 : kotlin.String?
        // --- key_str3 : kotlin.Int?
        Launcher().launch(com.mars.component.detail.java.ui.SecondActivity::class.java,queries,Path("activity/java_page2/{key_str1}/{key_str2}/{key_str3}").getParameters(path),results)
      }
      else -> { throw com.rubik.route.exception.BadPathOrVersionException(path)}
    }
  }

  @RGenerated(
    kind = "aggregate_companion",
    by = "rubik-kapt:1.9.0.1.T-K1_3-LOCAL",
    version = "0.1.3"
  )
  @Keep
  companion object : AggregateFactory() {
    override val URI: String = "demo://com.mars.rubik-test.detail-java"

    override val DEPENDENCIES: List<String> = listOf()

    override val EVENT_MSGS: List<String> = listOf()

    override val CREATOR: Function0<Aggregatable> = {DetailJavaAggregate()}
  }
}
