package rubik.generate.aggregate.demo_com_mars_rubik_test_detail

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
 * context uri: [demo://com.mars.rubik-test.detail]
 * version: 0.1.3
 */
@RGenerated(
  kind = "aggregate",
  by = "rubik-kapt:1.9.0.1.T-K1_3-LOCAL",
  version = "0.1.3"
)
@Keep
class DetailAggregate : Aggregatable {
  override fun onEvent(msg: String, queries: Queries) {
    when(msg){
      "LifeCycleEvent_Init" ->  {
        // com.mars.component.detail.event.EventsByInstance.onInit
        // - parameters:
        // --- arg : kotlin.Any
        // --- arg1 : kotlin.Any
        // --- arg2 : kotlin.Any
        // --- arg3 : kotlin.Any
        // - resultType:
        // --- null
        val arg = queries.toTypeOfT<kotlin.Any>(0, null)
        val arg1 = queries.toTypeOfT<kotlin.Any>(1, null)
        val arg2 = queries.toTypeOfT<kotlin.Any>(2, null)
        val arg3 = queries.toTypeOfT<kotlin.Any>(3, null)
        com.mars.component.detail.event.EventsByInstance().onInit(arg,arg1,arg2,arg3)
        // com.mars.component.detail.event.EventsWithLambda.init
        // - resultType:
        // --- kotlin.Unit
        com.mars.component.detail.event.EventsWithLambda().init()
        // com.mars.component.detail.event.Events.onInit
        // - parameters:
        // --- context : android.content.Context
        // - resultType:
        // --- null
        val context = queries.toTypeOfT<android.content.Context>(0, null)
        com.mars.component.detail.event.Events().onInit(context)
      }
      "LifeCycleEvent_Destroy" ->  {
        // com.mars.component.detail.event.EventsByInstance.onDestroy
        // - parameters:
        // --- arg : kotlin.Any
        // --- arg1 : kotlin.Any
        // --- arg2 : kotlin.Any
        // --- arg3 : kotlin.Any
        // - resultType:
        // --- null
        val arg = queries.toTypeOfT<kotlin.Any>(0, null)
        val arg1 = queries.toTypeOfT<kotlin.Any>(1, null)
        val arg2 = queries.toTypeOfT<kotlin.Any>(2, null)
        val arg3 = queries.toTypeOfT<kotlin.Any>(3, null)
        com.mars.component.detail.event.EventsByInstance().onDestroy(arg,arg1,arg2,arg3)
        // com.mars.component.detail.event.EventsWithLambda.destroy
        // - resultType:
        // --- kotlin.Unit
        com.mars.component.detail.event.EventsWithLambda().destroy()
        // com.mars.component.detail.event.Events.onDestroy
        // - parameters:
        // --- context : android.content.Context
        // - resultType:
        // --- null
        val context = queries.toTypeOfT<android.content.Context>(0, null)
        com.mars.component.detail.event.Events().onDestroy(context)
      }
      else -> {}
    }
  }

  override fun onRoute(
    path: String,
    queries: Queries,
    results: List<Results>
  ) {
    when {
      "doTestGenericity7" == path ->  {
        // com.mars.component.detail.test.TestGenericityTask.Companion.doTestGenericity7
        // - parameters:
        // --- data : kotlin.Array<kotlin.Long?>?
        // - resultType:
        // --- kotlin.Array<kotlin.Long?>?
        val data = queries.toTypeOfT<kotlin.Array<kotlin.Long?>?>(0, null)
        com.mars.component.detail.test.TestGenericityTask.Companion.doTestGenericity7(data).apply {
          results.set(0, Result(this))
        }
      }
      "testGenericity4" == path ->  {
        // com.mars.component.detail.test.TestGenericityTask.testGenericity4
        // - resultType:
        // --- kotlin.collections.List<kotlin.String?>
        com.mars.component.detail.test.TestGenericityTask().testGenericity4.apply {
          results.set(0, Result(this))
        }
      }
      "testGenericity5" == path ->  {
        // com.mars.component.detail.test.TestGenericityTask.testGenericity5
        // - parameters:
        // --- arg0 : kotlin.collections.List<kotlin.String?>?
        // - resultType:
        // --- kotlin.collections.List<kotlin.String?>?
        val arg0 = queries.toTypeOfT<kotlin.collections.List<kotlin.String?>?>(0, "arg0")
        com.mars.component.detail.test.TestGenericityTask().testGenericity5(arg0).apply {
          results.set(0, Result(this))
        }
      }
      "doTestGenericity6" == path ->  {
        // com.mars.component.detail.test.TestGenericityTask.doTestGenericity6
        // - parameters:
        // --- data : kotlin.Array<com.mars.component.detail.value.TestNullableBean?>?
        // - resultType:
        // --- kotlin.Array<com.mars.component.detail.value.TestNullableBean?>?
        val data = queries.toTypeOfT<kotlin.Array<com.mars.component.detail.value.TestNullableBean?>?>(0, null, true)
        com.mars.component.detail.test.TestGenericityTask().doTestGenericity6(data).apply {
          results.set(0, Result(this, true))
        }
      }
      "doTestGenericity1" == path ->  {
        // com.mars.component.detail.test.TestGenericityTaskKt.doTestGenericity1
        // - parameters:
        // --- data : kotlin.collections.List<com.mars.component.detail.value.TestNullableBean?>?
        // - resultType:
        // --- kotlin.collections.List<com.mars.component.detail.value.TestNullableBean?>?
        val data = queries.toTypeOfT<kotlin.collections.List<com.mars.component.detail.value.TestNullableBean?>?>(0, null, true)
        com.mars.component.detail.test.doTestGenericity1(data).apply {
          results.set(0, Result(this, true))
        }
      }
      "doTestGenericity2" == path ->  {
        // com.mars.component.detail.test.TestGenericityTaskKt.doTestGenericity2
        // - parameters:
        // --- data : androidx.lifecycle.LiveData<com.mars.component.detail.value.TestNullableBean?>?
        // - resultType:
        // --- androidx.lifecycle.LiveData<com.mars.component.detail.value.TestNullableBean?>?
        val data = queries.toTypeOfT<androidx.lifecycle.LiveData<com.mars.component.detail.value.TestNullableBean?>?>(0, null, true)
        com.mars.component.detail.test.doTestGenericity2(data).apply {
          results.set(0, Result(this, true))
        }
      }
      "doTestGenericity3" == path ->  {
        // com.mars.component.detail.test.TestGenericityTaskKt.doTestGenericity3
        // - parameters:
        // --- data : kotlin.collections.Map<kotlin.String, kotlin.Long?>
        // - resultType:
        // --- kotlin.collections.Map<kotlin.String, kotlin.Long?>
        val data = queries.toTypeOfT<kotlin.collections.Map<kotlin.String, kotlin.Long?>>(0, null)
        com.mars.component.detail.test.doTestGenericity3(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestGenericity8" == path ->  {
        // com.mars.component.detail.test.TestGenericityTaskKt.doTestGenericity8
        // - parameters:
        // --- resultSuccess : kotlin.Function1<kotlin.collections.ArrayList<kotlin.String>, kotlin.Unit>
        // - resultType:
        // --- null
        val resultSuccess = queries.toTypeOfT<kotlin.Function1<kotlin.collections.ArrayList<kotlin.String>, kotlin.Unit>>(0, null)
        com.mars.component.detail.test.doTestGenericity8(resultSuccess).apply {
          results.set(0, Result(this))
        }
      }
      "doTestGenericity9" == path ->  {
        // com.mars.component.detail.test.TestGenericityTaskKt.doTestGenericity9
        // - parameters:
        // --- resultSuccess : kotlin.Function1<kotlin.collections.List<kotlin.String>, kotlin.Unit>
        // - resultType:
        // --- null
        val resultSuccess = queries.toTypeOfT<kotlin.Function1<kotlin.collections.List<kotlin.String>, kotlin.Unit>>(0, null)
        com.mars.component.detail.test.doTestGenericity9(resultSuccess).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType1" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType1
        // - parameters:
        // --- data : android.content.Context?
        // - resultType:
        // --- android.content.Context?
        val data = queries.toTypeOfT<android.content.Context?>(0, null)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType1(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType2" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType2
        // - parameters:
        // --- data : kotlin.Int?
        // - resultType:
        // --- kotlin.Int?
        val data = queries.toTypeOfT<kotlin.Int?>(0, null)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType2(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType3" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType3
        // - parameters:
        // --- data : kotlin.String?
        // - resultType:
        // --- kotlin.String?
        val data = queries.toTypeOfT<kotlin.String?>(0, null)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType3(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType4" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType4
        // - parameters:
        // --- data : com.mars.component.detail.value.TestNullableBean?
        // - resultType:
        // --- com.mars.component.detail.value.TestNullableBean?
        val data = queries.toTypeOfT<com.mars.component.detail.value.TestNullableBean?>(0, null, true)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType4(data).apply {
          results.set(0, Result(this, true))
        }
      }
      "doTestDataType5" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType5
        // - parameters:
        // --- data : kotlin.collections.List<kotlin.String>?
        // - resultType:
        // --- kotlin.collections.List<kotlin.String>?
        val data = queries.toTypeOfT<kotlin.collections.List<kotlin.String>?>(0, null)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType5(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType6" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType6
        // - parameters:
        // --- data : kotlin.Array<kotlin.Long>?
        // - resultType:
        // --- kotlin.Array<kotlin.Long>?
        val data = queries.toTypeOfT<kotlin.Array<kotlin.Long>?>(0, null)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType6(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType7" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType7
        // - parameters:
        // --- data : kotlin.LongArray?
        // - resultType:
        // --- kotlin.LongArray?
        val data = queries.toTypeOfT<kotlin.LongArray?>(0, null)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType7(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType8" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType8
        // - parameters:
        // --- data : kotlin.IntArray?
        // - resultType:
        // --- kotlin.IntArray?
        val data = queries.toTypeOfT<kotlin.IntArray?>(0, null)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType8(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType9" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType9
        // - parameters:
        // --- data : kotlin.BooleanArray?
        // - resultType:
        // --- kotlin.BooleanArray?
        val data = queries.toTypeOfT<kotlin.BooleanArray?>(0, null)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType9(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType10" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType10
        // - parameters:
        // --- resultSuccess : kotlin.Function1<kotlin.Int, kotlin.Unit>
        // --- resultSuccess2 : kotlin.Function1<kotlin.String, kotlin.Unit>
        // - resultType:
        // --- null
        val resultSuccess = queries.toTypeOfT<kotlin.Function1<kotlin.Int, kotlin.Unit>>(0, null)
        val resultSuccess2 = queries.toTypeOfT<kotlin.Function1<kotlin.String, kotlin.Unit>>(1, null)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType10(resultSuccess,resultSuccess2).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType11" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType11
        // - parameters:
        // --- resultSuccess : kotlin.Function2<kotlin.Int, com.mars.component.detail.value.TestNullableBean, kotlin.Unit>
        // --- resultSuccess2 : kotlin.Function1<com.mars.component.detail.value.TestNullableBean?, kotlin.Unit>
        // - resultType:
        // --- null
        val resultSuccess : (kotlin.Any?,kotlin.Any?) -> Unit = { arg0,arg1 -> 
          results.set(0, Result(arg0),Result(arg1, true))
        }
        val resultSuccess2 : (kotlin.Any?) -> Unit = { arg0 -> 
          results.set(1, Result(arg0, true))
        }
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType11(resultSuccess,resultSuccess2)
      }
      "doTestDataType13" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType13
        // - parameters:
        // --- data : kotlin.collections.List<kotlin.Int?>
        // - resultType:
        // --- kotlin.collections.List<kotlin.Int?>
        val data = queries.toTypeOfT<kotlin.collections.List<kotlin.Int?>>(0, null)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType13(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType14" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType14
        // - parameters:
        // --- data : kotlin.collections.ArrayList<kotlin.Int?>
        // - resultType:
        // --- kotlin.collections.ArrayList<kotlin.Int?>
        val data = queries.toTypeOfT<kotlin.collections.ArrayList<kotlin.Int?>>(0, null)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType14(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType15" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType15
        // - parameters:
        // --- data : kotlin.collections.List<com.mars.component.detail.value.TestNullableBean?>
        // - resultType:
        // --- kotlin.collections.List<com.mars.component.detail.value.TestNullableBean?>
        val data = queries.toTypeOfT<kotlin.collections.List<com.mars.component.detail.value.TestNullableBean?>>(0, null, true)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType15(data).apply {
          results.set(0, Result(this, true))
        }
      }
      "doTestDataType16" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType16
        // - parameters:
        // --- data : kotlin.collections.ArrayList<com.mars.component.detail.value.TestNullableBean?>
        // - resultType:
        // --- kotlin.collections.ArrayList<com.mars.component.detail.value.TestNullableBean?>
        val data = queries.toTypeOfT<kotlin.collections.ArrayList<com.mars.component.detail.value.TestNullableBean?>>(0, null, true)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType16(data).apply {
          results.set(0, Result(this, true))
        }
      }
      "doTestDataType17" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType17
        // - parameters:
        // --- data : kotlin.collections.List<kotlin.String?>
        // - resultType:
        // --- kotlin.collections.List<kotlin.String?>
        val data = queries.toTypeOfT<kotlin.collections.List<kotlin.String?>>(0, null)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType17(data).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType18" == path ->  {
        // com.mars.component.detail.test.TestDataTypeTask.doTestDataType18
        // - parameters:
        // --- data : kotlin.collections.ArrayList<kotlin.String?>
        // - resultType:
        // --- kotlin.collections.ArrayList<kotlin.String?>
        val data = queries.toTypeOfT<kotlin.collections.ArrayList<kotlin.String?>>(0, null)
        com.mars.component.detail.test.TestDataTypeTask().doTestDataType18(data).apply {
          results.set(0, Result(this))
        }
      }
      "test-bean/create" == path ->  {
        // com.mars.component.detail.value.TestCreateBean.com.mars.component.detail.value.TestCreateBean
        // - parameters:
        // --- d1 : kotlin.Int?
        // --- d2 : kotlin.String?
        // - resultType:
        // --- com.mars.component.detail.value.TestCreateBean
        val d1 = queries.toTypeOfT<kotlin.Int?>(0, null)
        val d2 = queries.toTypeOfT<kotlin.String?>(1, null)
        com.mars.component.detail.value.TestCreateBean(d1,d2).apply {
          results.set(0, Result(this, true))
        }
      }
      "do-sth-ext" == path ->  {
        // com.mars.component.detail.api.ApisExtKt.doSthExt
        // - parameters:
        // --- s : kotlin.String
        // --- i : kotlin.Int
        // - resultType:
        // --- kotlin.Int
        val s = queries.toTypeOfT<kotlin.String>(0, null)
        val i = queries.toTypeOfT<kotlin.Int>(1, null)
        com.mars.component.detail.api.doSthExt(s,i).apply {
          results.set(0, Result(this))
        }
      }
      "doSthCompanion" == path ->  {
        // com.mars.component.detail.api.AipsInCompanion.DetailCompanion.doSthCompanion
        // - parameters:
        // --- i : kotlin.Int
        // --- j : kotlin.String
        // --- k : kotlin.Long
        // - resultType:
        // --- kotlin.String
        val i = queries.toTypeOfT<kotlin.Int>(0, null)
        val j = queries.toTypeOfT<kotlin.String>(1, null)
        val k = queries.toTypeOfT<kotlin.Long>(2, null)
        com.mars.component.detail.api.AipsInCompanion.DetailCompanion.doSthCompanion(i,j,k).apply {
          results.set(0, Result(this))
        }
      }
      "property/companion" == path ->  {
        // com.mars.component.detail.api.AipsInCompanion.propertyCompanion
        // - resultType:
        // --- kotlin.String
        com.mars.component.detail.api.AipsInCompanion.propertyCompanion.apply {
          results.set(0, Result(this))
        }
      }
      "doSthHOFCompanion" == path ->  {
        // com.mars.component.detail.api.AipsInCompanion.doSthHOFCompanion
        // - parameters:
        // --- arg0 : kotlin.Int
        // - resultType:
        // --- kotlin.Int
        val arg0 = queries.toTypeOfT<kotlin.Int>(0, "arg0")
        com.mars.component.detail.api.AipsInCompanion.doSthHOFCompanion(arg0).apply {
          results.set(0, Result(this))
        }
      }
      "2.0/do-sth-hof" == path ->  {
        // com.mars.component.detail.api.Apis.doSthHOF
        // - parameters:
        // --- arg0 : kotlin.Int
        // --- arg1 : kotlin.String
        // --- arg2 : com.mars.component.detail.value.TestDataBean
        // - resultType:
        // --- kotlin.Int
        val arg0 = queries.toTypeOfT<kotlin.Int>(0, "arg0")
        val arg1 = queries.toTypeOfT<kotlin.String>(1, "arg1")
        val arg2 = queries.toTypeOfT<com.mars.component.detail.value.TestDataBean>(2, "arg2", true)
        com.mars.component.detail.api.Apis().doSthHOF(arg0,arg1,arg2).apply {
          results.set(0, Result(this))
        }
      }
      "1.0/property/property" == path ->  {
        // com.mars.component.detail.api.Apis.property
        // - resultType:
        // --- kotlin.String
        com.mars.component.detail.api.Apis().property.apply {
          results.set(0, Result(this))
        }
      }
      "do-sth" == path ->  {
        // com.mars.component.detail.api.Apis.doSth
        // - resultType:
        // --- null
        com.mars.component.detail.api.Apis().doSth().apply {
          results.set(0, Result(this))
        }
      }
      "view/get" == path ->  {
        // com.mars.component.detail.api.Apis.getView
        // - parameters:
        // --- context : android.content.Context
        // - resultType:
        // --- android.view.View?
        val context = queries.toTypeOfT<android.content.Context>(0, null)
        com.mars.component.detail.api.Apis().getView(context).apply {
          results.set(0, Result(this))
        }
      }
      Path("sth/{id}/a-{name}?code1={code1}&code2={code2}").matching(path) ->  {
        queries.addAll(Path("sth/{id}/a-{name}?code1={code1}&code2={code2}").getParameters(path))
        // com.mars.component.detail.api.Apis.getSth
        // - parameters:
        // --- id : kotlin.String
        // --- name : kotlin.String
        // --- code1 : kotlin.String
        // --- code2 : kotlin.String
        // - resultType:
        // --- kotlin.String
        val id = queries.toTypeOfT<kotlin.String>(0, null)
        val name = queries.toTypeOfT<kotlin.String>(1, null)
        val code1 = queries.toTypeOfT<kotlin.String>(2, null)
        val code2 = queries.toTypeOfT<kotlin.String>(3, null)
        com.mars.component.detail.api.Apis().getSth(id,name,code1,code2).apply {
          results.set(0, Result(this))
        }
      }
      Path("sth-navigation-only/{uri}").matching(path) ->  {
        queries.addAll(Path("sth-navigation-only/{uri}").getParameters(path))
        // com.mars.component.detail.api.Apis.getSthNavigationOnly
        // - parameters:
        // --- uri : kotlin.String
        // - resultType:
        // --- kotlin.String
        val uriRubikParameter = queries.toTypeOfT<kotlin.String>(0, null)
        com.mars.component.detail.api.Apis().getSthNavigationOnly(uriRubikParameter).apply {
          results.set(0, Result(this))
        }
      }
      "doSthVararg" == path ->  {
        // com.mars.component.detail.api.Apis.doSthVararg
        // - parameters:
        // --- no : kotlin.Int
        // --- varargString : vararg kotlin.Array<kotlin.String>
        // - resultType:
        // --- null
        val no = queries.toTypeOfT<kotlin.Int>(0, null)
        val varargString = queries.toTypeOfT<kotlin.Array<kotlin.String>>(1, null)
        com.mars.component.detail.api.Apis().doSthVararg(no,*varargString).apply {
          results.set(0, Result(this))
        }
      }
      "doSthBean" == path ->  {
        // com.mars.component.detail.api.Apis.doSthBean
        // - parameters:
        // --- a1 : com.mars.component.detail.value.TestDataBean
        // - resultType:
        // --- com.mars.component.detail.value.TestListBean
        val a1 = queries.toTypeOfT<com.mars.component.detail.value.TestDataBean>(0, null, true)
        com.mars.component.detail.api.Apis().doSthBean(a1).apply {
          results.set(0, Result(this, true))
        }
      }
      "doSthAsyncHOF" == path ->  {
        // com.mars.component.detail.api.ApisAsyncReturn.doSthAsyncHOF
        // - parameters:
        // --- hof : kotlin.Function2<kotlin.String, com.mars.component.detail.value.TestDataBean, kotlin.Unit>
        // - resultType:
        // --- null
        val hof : (kotlin.Any?,kotlin.Any?) -> Unit = { arg0,arg1 -> 
          results.set(0, Result(arg0),Result(arg1, true))
        }
        com.mars.component.detail.api.ApisAsyncReturn().doSthAsyncHOF(hof)
      }
      "doSthAsyncHOFNullable" == path ->  {
        // com.mars.component.detail.api.ApisAsyncReturn.doSthAsyncHOFNullable
        // - parameters:
        // --- hof : kotlin.Function2<kotlin.String, com.mars.component.detail.value.TestDataBean, kotlin.Unit>?
        // --- hof2p : kotlin.Function2<kotlin.String, com.mars.component.detail.value.TestDataBean, kotlin.Unit>?
        // - resultType:
        // --- null
        val hof : (kotlin.Any?,kotlin.Any?) -> Unit = { arg0,arg1 -> 
          results.set(0, Result(arg0),Result(arg1, true))
        }
        val hof2p = queries.toTypeOfT<kotlin.Function2<kotlin.String, com.mars.component.detail.value.TestDataBean, kotlin.Unit>?>(0, null, true)
        com.mars.component.detail.api.ApisAsyncReturn().doSthAsyncHOFNullable(hof,hof2p)
      }
      "doSthAsync2HOF" == path ->  {
        // com.mars.component.detail.api.ApisAsyncReturn.doSthAsyncHOF
        // - parameters:
        // --- hof : kotlin.Function2<kotlin.String, kotlin.Int, kotlin.Unit>
        // --- hof2 : kotlin.Function2<kotlin.String, kotlin.Int, kotlin.Unit>
        // - resultType:
        // --- null
        val hof : (kotlin.Any?,kotlin.Any?) -> Unit = { arg0,arg1 -> 
          results.set(0, Result(arg0),Result(arg1))
        }
        val hof2 : (kotlin.Any?,kotlin.Any?) -> Unit = { arg0,arg1 -> 
          results.set(1, Result(arg0),Result(arg1))
        }
        com.mars.component.detail.api.ApisAsyncReturn().doSthAsyncHOF(hof,hof2)
      }
      "doSthAsyncOpen" == path ->  {
        // com.mars.component.detail.api.ApisAsyncReturn.doSthAsyncOpen
        // - parameters:
        // --- uri : kotlin.String
        // --- results : com.mars.component.detail.api.Callback?
        // - resultType:
        // --- null
        val uriRubikParameter = queries.toTypeOfT<kotlin.String>(0, null)
        val resultsRubikParameter = object : com.mars.component.detail.api.Callback() {
          override fun onCall(v1: kotlin.String?,v2: kotlin.Int) {
            results.set(0, Result(v1),Result(v2))
          }
        }
        com.mars.component.detail.api.ApisAsyncReturn().doSthAsyncOpen(uriRubikParameter,resultsRubikParameter)
      }
      "do-sth-async-interface" == path ->  {
        // com.mars.component.detail.api.ApisAsyncReturn.doSthAsyncInterface
        // - parameters:
        // --- onResult : com.mars.component.detail.api.Callbackable
        // - resultType:
        // --- null
        val onResult = object : com.mars.component.detail.api.Callbackable {
          override fun onCall(v1: kotlin.String?,v2: kotlin.Int) {
            results.set(0, Result(v1),Result(v2))
          }
        }
        com.mars.component.detail.api.ApisAsyncReturn().doSthAsyncInterface(onResult)
      }
      "do-sth-async-2-interface" == path ->  {
        // com.mars.component.detail.api.ApisAsyncReturn.doSthAsync2Interface
        // - parameters:
        // --- onResult : com.mars.component.detail.api.Callbackable
        // --- onResult2 : com.mars.component.detail.api.Callbackable
        // --- onResult3 : com.mars.component.detail.api.BeanCallbackable
        // - resultType:
        // --- null
        val onResult = object : com.mars.component.detail.api.Callbackable {
          override fun onCall(v1: kotlin.String?,v2: kotlin.Int) {
            results.set(0, Result(v1),Result(v2))
          }
        }
        val onResult2 = object : com.mars.component.detail.api.Callbackable {
          override fun onCall(v1: kotlin.String?,v2: kotlin.Int) {
            results.set(1, Result(v1),Result(v2))
          }
        }
        val onResult3 = object : com.mars.component.detail.api.BeanCallbackable {
          override fun onCall(v: com.mars.component.detail.value.TestDataBean?) {
            results.set(2, Result(v, true))
          }
        }
        com.mars.component.detail.api.ApisAsyncReturn().doSthAsync2Interface(onResult,onResult2,onResult3)
      }
      "doSthHOFTop" == path ->  {
        // com.mars.component.detail.api.AipsInTopKt.doSthHOFTop
        // - parameters:
        // --- arg0 : kotlin.Unit
        // - resultType:
        // --- kotlin.Unit
        val arg0 = queries.toTypeOfT<kotlin.Unit>(0, "arg0")
        com.mars.component.detail.api.doSthHOFTop(arg0).apply {
          results.set(0, Result(this))
        }
      }
      "property/top" == path ->  {
        // com.mars.component.detail.api.AipsInTopKt.propertyTop
        // - resultType:
        // --- kotlin.String
        com.mars.component.detail.api.propertyTop.apply {
          results.set(0, Result(this))
        }
      }
      "doSthTop" == path ->  {
        // com.mars.component.detail.api.AipsInTopKt.doSthTop
        // - parameters:
        // --- ints : kotlin.Array<kotlin.Int>
        // --- li : kotlin.collections.List<kotlin.Int?>
        // --- strings : kotlin.Array<kotlin.String>
        // --- ls : kotlin.collections.List<kotlin.String>
        // --- beans : kotlin.Array<com.mars.component.detail.value.TestDataBean>
        // --- lb : kotlin.collections.List<com.mars.component.detail.value.TestDataBean>
        // - resultType:
        // --- kotlin.collections.List<com.mars.component.detail.value.TestDataBean>
        val ints = queries.toTypeOfT<kotlin.Array<kotlin.Int>>(0, null)
        val li = queries.toTypeOfT<kotlin.collections.List<kotlin.Int?>>(1, null)
        val strings = queries.toTypeOfT<kotlin.Array<kotlin.String>>(2, null)
        val ls = queries.toTypeOfT<kotlin.collections.List<kotlin.String>>(3, null)
        val beans = queries.toTypeOfT<kotlin.Array<com.mars.component.detail.value.TestDataBean>>(4, null, true)
        val lb = queries.toTypeOfT<kotlin.collections.List<com.mars.component.detail.value.TestDataBean>>(5, null, true)
        com.mars.component.detail.api.doSthTop(ints,li,strings,ls,beans,lb).apply {
          results.set(0, Result(this, true))
        }
      }
      "do-sth-provide-instance-by-func" == path ->  {
        // com.mars.component.detail.api.Task.doSthProvideObject
        // - resultType:
        // --- null
        com.mars.component.detail.api.provideTask().doSthProvideObject().apply {
          results.set(0, Result(this))
        }
      }
      "do-sth-provide-instance-by-func2" == path ->  {
        // com.mars.component.detail.api.Task.doSthProvideObject2
        // - resultType:
        // --- null
        com.mars.component.detail.api.provideTask().doSthProvideObject2().apply {
          results.set(0, Result(this))
        }
      }
      "do-sth-provide-instance-by-parameter-func" == path ->  {
        // com.mars.component.detail.api.Task.doSthProvideObject2
        // - parameters:
        // --- v1 : kotlin.String
        // --- v2 : kotlin.Int?
        // --- v3 : kotlin.Int?
        // - resultType:
        // --- null
        val rubikAssistValue = queries.toTypeOfT<kotlin.String>(0, null)
        val rubikAssistV0 = queries.toTypeOfT<kotlin.Int?>(1, null)
        val v1 = queries.toTypeOfT<kotlin.String>(2, null)
        val v2 = queries.toTypeOfT<kotlin.Int?>(3, null)
        val v3 = queries.toTypeOfT<kotlin.Int?>(4, null)
        com.mars.component.detail.api.provideTask2(rubikAssistValue,rubikAssistV0).doSthProvideObject2(v1,v2,v3).apply {
          results.set(0, Result(this))
        }
      }
      "do-sth-provide-instance-by-constructor" == path ->  {
        // com.mars.component.detail.api.Task.doSthProvideObject3
        // - parameters:
        // --- v1 : kotlin.String
        // --- v2 : kotlin.Int?
        // --- v3 : kotlin.Int?
        // - resultType:
        // --- null
        val rubikAssistValue = queries.toTypeOfT<kotlin.String>(0, null)
        val v1 = queries.toTypeOfT<kotlin.String>(1, null)
        val v2 = queries.toTypeOfT<kotlin.Int?>(2, null)
        val v3 = queries.toTypeOfT<kotlin.Int?>(3, null)
        com.mars.component.detail.api.Task(rubikAssistValue).doSthProvideObject3(v1,v2,v3).apply {
          results.set(0, Result(this))
        }
      }
      "doSthResultReceiver" == path ->  {
        // com.mars.component.detail.api.ApisWithResultReceiver.doSthResultReceiver
        // - parameters:
        // --- result : android.os.ResultReceiver
        // - resultType:
        // --- android.os.ResultReceiver
        val resultRubikParameter = queries.toTypeOfT<android.os.ResultReceiver>(0, null)
        com.mars.component.detail.api.ApisWithResultReceiver().doSthResultReceiver(resultRubikParameter).apply {
          results.set(0, Result(this))
        }
      }
      "live-data/get" == path ->  {
        // com.mars.component.detail.api.ApisWithLiveData.getSthLiveData
        // - resultType:
        // --- androidx.lifecycle.LiveData<kotlin.String>
        com.mars.component.detail.api.ApisWithLiveData().getSthLiveData().apply {
          results.set(0, Result(this))
        }
      }
      "live-data-bean/get" == path ->  {
        // com.mars.component.detail.api.ApisWithLiveData.getSthLiveDataBean
        // - resultType:
        // --- androidx.lifecycle.LiveData<com.mars.component.detail.value.TestDataBean>
        com.mars.component.detail.api.ApisWithLiveData().getSthLiveDataBean().apply {
          results.set(0, Result(this, true))
        }
      }
      "live-data-bean-list/get" == path ->  {
        // com.mars.component.detail.api.ApisWithLiveData.getSthLiveDataBeanList
        // - resultType:
        // --- androidx.lifecycle.LiveData<kotlin.collections.List<com.mars.component.detail.value.TestListBean>>
        com.mars.component.detail.api.ApisWithLiveData().getSthLiveDataBeanList().apply {
          results.set(0, Result(this, true))
        }
      }
      "fragment/page1" == path ->  {
        // com.mars.component.detail.ui.FirstPageFragment.com.mars.component.detail.ui.FirstPageFragment
        // - resultType:
        // --- com.mars.component.detail.ui.FirstPageFragment
        com.mars.component.detail.ui.FirstPageFragment().apply {
          results.set(0, Result(this))
        }
      }
      "api/serialization/parcel-bean" == path ->  {
        // com.mars.component.detail.api.ApiSerialization.doSthParcelBean
        // - parameters:
        // --- parcelBean : com.mars.component.detail.value.TestParcelizeBean?
        // - resultType:
        // --- com.mars.component.detail.value.TestParcelizeBean
        val parcelBean = queries.toTypeOfT<com.mars.component.detail.value.TestParcelizeBean?>(0, null, true)
        com.mars.component.detail.api.ApiSerialization().doSthParcelBean(parcelBean).apply {
          results.set(0, Result(this, true))
        }
      }
      "api/serialization/parcel-array" == path ->  {
        // com.mars.component.detail.api.ApiSerialization.doSthParcelArray
        // - parameters:
        // --- parcels : kotlin.Array<com.mars.component.detail.value.TestParcelizeBean>
        // - resultType:
        // --- kotlin.Array<com.mars.component.detail.value.TestParcelizeBean>
        val parcels = queries.toTypeOfT<kotlin.Array<com.mars.component.detail.value.TestParcelizeBean>>(0, null, true)
        com.mars.component.detail.api.ApiSerialization().doSthParcelArray(parcels).apply {
          results.set(0, Result(this, true))
        }
      }
      "api/serialization/parcel-list" == path ->  {
        // com.mars.component.detail.api.ApiSerialization.doSthParcelList
        // - parameters:
        // --- parcels : kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>
        // - resultType:
        // --- kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>
        val parcels = queries.toTypeOfT<kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>>(0, null, true)
        com.mars.component.detail.api.ApiSerialization().doSthParcelList(parcels).apply {
          results.set(0, Result(this, true))
        }
      }
      "api/serialization/Serializable-bean" == path ->  {
        // com.mars.component.detail.api.ApiSerialization.doSthSerializableBean
        // - parameters:
        // --- serializableBean : com.mars.component.detail.value.TestSerializableBean
        // - resultType:
        // --- com.mars.component.detail.value.TestSerializableBean
        val serializableBean = queries.toTypeOfT<com.mars.component.detail.value.TestSerializableBean>(0, null, true)
        com.mars.component.detail.api.ApiSerialization().doSthSerializableBean(serializableBean).apply {
          results.set(0, Result(this, true))
        }
      }
      "api/bigdata/json-array" == path ->  {
        // com.mars.component.detail.api.ApiBigData.getBigJsonArray
        // - parameters:
        // --- array : kotlin.Array<com.mars.component.detail.value.TestDataBean>
        // - resultType:
        // --- kotlin.Array<com.mars.component.detail.value.TestDataBean>
        val array = queries.toTypeOfT<kotlin.Array<com.mars.component.detail.value.TestDataBean>>(0, null, true)
        com.mars.component.detail.api.ApiBigData().getBigJsonArray(array).apply {
          results.set(0, Result(this, true))
        }
      }
      "api/bigdata/parcel-array" == path ->  {
        // com.mars.component.detail.api.ApiBigData.getBigParcelArray
        // - parameters:
        // --- array : kotlin.Array<com.mars.component.detail.value.TestParcelizeBean>
        // - resultType:
        // --- kotlin.Array<com.mars.component.detail.value.TestParcelizeBean>
        val array = queries.toTypeOfT<kotlin.Array<com.mars.component.detail.value.TestParcelizeBean>>(0, null, true)
        com.mars.component.detail.api.ApiBigData().getBigParcelArray(array).apply {
          results.set(0, Result(this, true))
        }
      }
      "api/bigdata/lib-array" == path ->  {
        // com.mars.component.detail.api.ApiBigData.getBigLibArray
        // - parameters:
        // --- array : kotlin.Array<com.mars.util_library.TestLibDataBean>
        // - resultType:
        // --- kotlin.Array<com.mars.util_library.TestLibDataBean>
        val array = queries.toTypeOfT<kotlin.Array<com.mars.util_library.TestLibDataBean>>(0, null)
        com.mars.component.detail.api.ApiBigData().getBigLibArray(array).apply {
          results.set(0, Result(this))
        }
      }
      "api/bigdata/json-list" == path ->  {
        // com.mars.component.detail.api.ApiBigData.getBigJsonList
        // - parameters:
        // --- list : kotlin.collections.List<com.mars.component.detail.value.TestDataBean>
        // - resultType:
        // --- kotlin.collections.List<com.mars.component.detail.value.TestDataBean>
        val list = queries.toTypeOfT<kotlin.collections.List<com.mars.component.detail.value.TestDataBean>>(0, null, true)
        com.mars.component.detail.api.ApiBigData().getBigJsonList(list).apply {
          results.set(0, Result(this, true))
        }
      }
      "api/bigdata/parcel-list" == path ->  {
        // com.mars.component.detail.api.ApiBigData.getBigParcelList
        // - parameters:
        // --- list : kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>
        // - resultType:
        // --- kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>
        val list = queries.toTypeOfT<kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>>(0, null, true)
        com.mars.component.detail.api.ApiBigData().getBigParcelList(list).apply {
          results.set(0, Result(this, true))
        }
      }
      "api/bigdata/lib-list" == path ->  {
        // com.mars.component.detail.api.ApiBigData.getBigLibList
        // - parameters:
        // --- list : kotlin.collections.List<com.mars.util_library.TestLibDataBean>
        // - resultType:
        // --- kotlin.collections.List<com.mars.util_library.TestLibDataBean>
        val list = queries.toTypeOfT<kotlin.collections.List<com.mars.util_library.TestLibDataBean>>(0, null)
        com.mars.component.detail.api.ApiBigData().getBigLibList(list).apply {
          results.set(0, Result(this))
        }
      }
      "api/bigdata/parcel-arraylist" == path ->  {
        // com.mars.component.detail.api.ApiBigData.getBigParcelArrayList
        // - parameters:
        // --- list : kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>
        // - resultType:
        // --- kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>
        val list = queries.toTypeOfT<kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>>(0, null, true)
        com.mars.component.detail.api.ApiBigData().getBigParcelArrayList(list).apply {
          results.set(0, Result(this, true))
        }
      }
      Path("activity/page2/{key_str1}/{key_str2}/{key_str3}").matching(path) ->  {
        // com.mars.component.detail.ui.SecondPageActivity
        // - parameters:
        // --- key_str1 : kotlin.String?
        // --- key_str2 : kotlin.String?
        // --- key_str3 : kotlin.String?
        Launcher().launch(com.mars.component.detail.ui.SecondPageActivity::class.java,queries,Path("activity/page2/{key_str1}/{key_str2}/{key_str3}").getParameters(path),results)
      }
      "activity/page1_from_detail" == path ->  {
        // com.mars.component.detail.ui.FirstPageActivity
        // - parameters:
        // --- key_1_pa : com.mars.component.detail.value.TestCompanionBean?
        // --- key_2_pa_ar : kotlin.Array<com.mars.component.detail.value.TestCompanionBean>?
        // --- key_3_int : kotlin.Int
        // --- key_4_strs : kotlin.collections.List<kotlin.String>?
        // --- key_5_bean : com.mars.component.detail.value.TestDataBean?
        // --- key_a_ints : kotlin.collections.List<kotlin.Int>?
        // --- key_c_pa_li : kotlin.collections.List<com.mars.component.detail.value.TestCompanionBean>?
        // --- key_int_for_all : kotlin.Int?
        // --- key_int_for_detail : kotlin.Int?
        // --- uri : kotlin.String?
        Launcher().launch(com.mars.component.detail.ui.FirstPageActivity::class.java,queries,null,results)
      }
      "activity/page1_from_home" == path ->  {
        // com.mars.component.detail.ui.FirstPageActivity
        // - parameters:
        // --- key_1_pa : com.mars.component.detail.value.TestCompanionBean?
        // --- key_2_pa_ar : kotlin.Array<com.mars.component.detail.value.TestCompanionBean>?
        // --- key_3_int : kotlin.Int
        // --- key_4_strs : kotlin.collections.List<kotlin.String>?
        // --- key_5_bean : com.mars.component.detail.value.TestDataBean?
        // --- key_a_ints : kotlin.collections.List<kotlin.Int>?
        // --- key_c_pa_li : kotlin.collections.List<com.mars.component.detail.value.TestCompanionBean>?
        // --- key_int_for_all : kotlin.Int?
        // --- key_int_for_home : kotlin.Int?
        // --- uri : kotlin.String?
        Launcher().launch(com.mars.component.detail.ui.FirstPageActivity::class.java,queries,null,results)
      }
      "activity/page1" == path ->  {
        // com.mars.component.detail.ui.FirstPageActivity
        // - parameters:
        // --- key_1_pa : com.mars.component.detail.value.TestCompanionBean?
        // --- key_2_pa_ar : kotlin.Array<com.mars.component.detail.value.TestCompanionBean>?
        // --- key_3_int : kotlin.Int
        // --- key_4_strs : kotlin.collections.List<kotlin.String>?
        // --- key_5_bean : com.mars.component.detail.value.TestDataBean?
        // --- key_a_ints : kotlin.collections.List<kotlin.Int>?
        // --- key_c_pa_li : kotlin.collections.List<com.mars.component.detail.value.TestCompanionBean>?
        // --- uri : kotlin.String?
        Launcher().launch(com.mars.component.detail.ui.FirstPageActivity::class.java,queries,null,results)
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
    override val URI: String = "demo://com.mars.rubik-test.detail"

    override val DEPENDENCIES: List<String> = listOf()

    override val EVENT_MSGS: List<String> = listOf("LifeCycleEvent_Init","LifeCycleEvent_Destroy")

    override val CREATOR: Function0<Aggregatable> = {DetailAggregate()}
  }
}
