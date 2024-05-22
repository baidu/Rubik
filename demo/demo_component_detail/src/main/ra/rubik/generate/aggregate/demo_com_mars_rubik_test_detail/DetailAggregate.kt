package rubik.generate.aggregate.demo_com_mars_rubik_test_detail

import android.app.Activity
import android.content.Context
import android.os.ResultReceiver
import android.view.View
import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelStoreOwner
import com.mars.component.detail.api.AipsInCompanion
import com.mars.component.detail.api.ApiSerialization
import com.mars.component.detail.api.Apis
import com.mars.component.detail.api.ApisAsyncReturn
import com.mars.component.detail.api.ApisBadCase
import com.mars.component.detail.api.ApisBigData
import com.mars.component.detail.api.ApisWithLiveData
import com.mars.component.detail.api.ApisWithResultReceiver
import com.mars.component.detail.api.BeanCallbackable
import com.mars.component.detail.api.Callback
import com.mars.component.detail.api.Callbackable
import com.mars.component.detail.api.GetFileMetaCallback
import com.mars.component.detail.api.MultiCallback
import com.mars.component.detail.api.Task
import com.mars.component.detail.event.Events
import com.mars.component.detail.event.EventsWithLambda
import com.mars.component.detail.test.TestDataTypeTask
import com.mars.component.detail.test.TestDefaultPathTask
import com.mars.component.detail.test.TestGenericityTask
import com.mars.component.detail.ui.FirstPageActivity
import com.mars.component.detail.ui.FirstPageFragment
import com.mars.component.detail.ui.SecondPageActivity
import com.rubik.annotations.source.RAggregate
import com.rubik.annotations.source.RGenerated
import com.rubik.context.Aggregatable
import com.rubik.context.AggregateFactory
import com.rubik.identity.RAggregateId
import com.rubik.route.mapping.castToTypeOfT
import com.rubik.route.mapping.mapToType
import com.rubik.route.mapping.toTypeOfT
import kotlin.Any
import kotlin.Array
import kotlin.BooleanArray
import kotlin.Function0
import kotlin.Function1
import kotlin.Function2
import kotlin.Function3
import kotlin.Function5
import kotlin.Int
import kotlin.IntArray
import kotlin.Long
import kotlin.LongArray
import kotlin.Pair
import kotlin.String
import kotlin.Unit
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.Set
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toCallbackTestInterfaceOrgMapping
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toNullableObjectTestCtxMapping
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toNullableObjectTestOrgMapping
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toNullableTestDataMappingBeanCtxMapping
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toNullableTestDataMappingBeanOrgMapping
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toNullableTestNestDataMappingBeanCtxMapping
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toNullableTestNestDataMappingBeanOrgMapping
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toObjectTestCreateCtxMapping
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toObjectTestCreateOrgMapping
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toObjectTestCtxMapping
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toObjectTestOrgMapping
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toTestDataMappingBeanCtxMapping
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toTestDataMappingBeanOrgMapping
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toTestNestDataMappingBeanCtxMapping
import rubik.generate.aggregate.demo_com_mars_rubik_test_detail.DetailAggregate.Mappings.toTestNestDataMappingBeanOrgMapping
import rubik.generate.context.demo_com_mars_rubik_test_detail.DetailRouteActions
import com.mars.component.detail.callback.CallbackTestDefault as CallbackTestDefaultOrg
import com.mars.component.detail.callback.CallbackTestInterface as CallbackTestInterfaceOrg
import com.mars.component.detail.objekt.ObjectTestCreate as ObjectTestCreateOrg
import com.mars.component.detail.objekt.ObjectTest as ObjectTestOrg
import com.mars.component.detail.value.TestCompanionBean as TestCompanionBeanOrg
import com.mars.component.detail.value.TestCreateBean as TestCreateBeanOrg
import com.mars.component.detail.value.TestDataBean as TestDataBeanOrg
import com.mars.component.detail.value.TestListBean as TestListBeanOrg
import com.mars.component.detail.value.TestNullableBean as TestNullableBeanOrg
import com.mars.component.detail.value.TestParcelizeBean as TestParcelizeBeanOrg
import com.mars.component.detail.value.TestSerializableBean as TestSerializableBeanOrg
import com.mars.component.detail.value.mapping.TestDataMappingBean as TestDataMappingBeanOrg
import com.mars.component.detail.value.mapping.TestNestDataMappingBean as TestNestDataMappingBeanOrg
import com.rubik.activity.Launcher as RubikLauncher
import com.rubik.route.Queries as RubikQueries
import com.rubik.route.ResultGroups as RubikResultGroups
import com.rubik.route.Result as RubikResult
import com.rubik.router.uri.Path as RubikPath
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestCompanionBean as TestCompanionBeanCtx
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestCreateBean as TestCreateBeanCtx
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean as TestDataBeanCtx
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataMappingBean as TestDataMappingBeanCtx
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestListBean as TestListBeanCtx
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestNestDataMappingBean as TestNestDataMappingBeanCtx
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean as TestNullableBeanCtx
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestParcelizeBean as TestParcelizeBeanCtx
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestSerializableBean as TestSerializableBeanCtx
import rubik.generate.context.demo_com_mars_rubik_test_detail.callback.CallbackTestDefault as CallbackTestDefaultCtx
import rubik.generate.context.demo_com_mars_rubik_test_detail.callback.CallbackTestInterface as CallbackTestInterfaceCtx
import rubik.generate.context.demo_com_mars_rubik_test_detail.objekt.ObjectTestCreate as ObjectTestCreateCtx
import rubik.generate.context.demo_com_mars_rubik_test_detail.objekt.ObjectTest as ObjectTestCtx

/**
 * aggregate router function and router event of Rubik Context.
 *
 * uri: [demo://com.mars.rubik-test.detail] 
 * version: 0.0.1-DEV
 */
@RGenerated(
  kind = "aggregate",
  by = "rubik-kapt:1.10.0.0-K1_5-LOCAL",
  version = "0.0.1-DEV"
)
@Keep
class DetailAggregate : Aggregatable, DetailRouteActions {
  override fun onEvent(msg: String, queries: RubikQueries) {
    when (msg){
      "MY_INIT" ->  {
        // com.mars.component.detail.event.EventsByInstance.onInit
        // - parameters:
        // --- arg1 : kotlin.Any
        // --- arg2 : kotlin.Any
        // --- arg3 : kotlin.Any
        // - resultType:
        // --- null
        val rubikInstanceBool = queries.value(0, null)
        val arg1 = queries.value(1, null)
        val arg2 = queries.value(2, null)
        val arg3 = queries.value(3, null)
        com.mars.component.detail.event.provideEventsInstance(
          rubikInstanceBool.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Boolean] */ 
        ) /* -> NO NEED TO MAPPING :[com.mars.component.detail.event.EventsByInstance] */ .onInit(
          arg1.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Any] */ ,
          arg2.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Any] */ ,
          arg3.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Any] */ 
        )}
      "MY_DESTROY" ->  {
        // com.mars.component.detail.event.EventsByInstance.onDestroy
        // - parameters:
        // --- arg1 : kotlin.Any
        // --- arg2 : kotlin.Any
        // --- arg3 : kotlin.Any
        // - resultType:
        // --- null
        val rubikInstanceBool = queries.value(0, null)
        val arg1 = queries.value(1, null)
        val arg2 = queries.value(2, null)
        val arg3 = queries.value(3, null)
        com.mars.component.detail.event.provideEventsInstance(
          rubikInstanceBool.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Boolean] */ 
        ) /* -> NO NEED TO MAPPING :[com.mars.component.detail.event.EventsByInstance] */ .onDestroy(
          arg1.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Any] */ ,
          arg2.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Any] */ ,
          arg3.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Any] */ 
        )}
      "MY_CALLBACK_RES" ->  {
        // com.mars.component.detail.event.EventsByInstance.onCallbackRes
        // - parameters:
        // --- arg1 : kotlin.Any
        // --- result : kotlin.Function1<kotlin.Int, kotlin.Unit>
        // - resultType:
        // --- null
        val rubikInstanceBool = queries.value(0, null)
        val arg1 = queries.value(1, null)
        val resultCallback = queries.value(2, null)
        com.mars.component.detail.event.provideEventsInstance(
          rubikInstanceBool.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Boolean] */ 
        ) /* -> NO NEED TO MAPPING :[com.mars.component.detail.event.EventsByInstance] */ .onCallbackRes(
          arg1.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Any] */ ,
          resultCallback.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Function1<kotlin.Int, kotlin.Unit>] */ 
        )}
      "MY_CALLBACK_RES_BEAN" ->  {
        // com.mars.component.detail.event.EventsByInstance.onCallbackResBean
        // - parameters:
        // --- arg1 : kotlin.Any
        // --- result : kotlin.Function1<com.mars.component.detail.value.TestDataBean, kotlin.Unit>
        // - resultType:
        // --- null
        val rubikInstanceBool = queries.value(0, null)
        val arg1 = queries.value(1, null)
        val resultCallback = queries.value(2, null).castToTypeOfT<Function1<TestDataBeanCtx, Unit>>()
        val resultCallbackCallbackTransformer: (Any?) -> Unit = { lambdaArg0 -> 
          resultCallback(
            lambdaArg0.toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean] */ 
          )}
        com.mars.component.detail.event.provideEventsInstance(
          rubikInstanceBool.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Boolean] */ 
        ) /* -> NO NEED TO MAPPING :[com.mars.component.detail.event.EventsByInstance] */ .onCallbackResBean(
          arg1.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Any] */ ,
          resultCallbackCallbackTransformer.castToTypeOfT()
        )}
      "MY_CALLBACK_BEAN" ->  {
        // com.mars.component.detail.event.EventsByInstance.onCallbackBean
        // - parameters:
        // --- arg1 : kotlin.Any
        // --- bean : com.mars.component.detail.value.TestDataBean
        // - resultType:
        // --- null
        val rubikInstanceBool = queries.value(0, null)
        val arg1 = queries.value(1, null)
        val bean = queries.value(2, null)
        com.mars.component.detail.event.provideEventsInstance(
          rubikInstanceBool.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Boolean] */ 
        ) /* -> NO NEED TO MAPPING :[com.mars.component.detail.event.EventsByInstance] */ .onCallbackBean(
          arg1.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Any] */ ,
          bean.toTypeOfT() /* -> TO ORIGINAL TYPE :[com.mars.component.detail.value.TestDataBean] */ 
        )}
      "LifeCycleEvent_Init" ->  {
        // com.mars.component.detail.event.EventsWithLambda.init
        // - resultType:
        // --- kotlin.Unit
        EventsWithLambda().init() /* -> NO NEED TO MAPPING :[kotlin.Unit] */ // com.mars.component.detail.event.Events.onInit
        // - parameters:
        // --- context : android.content.Context
        // --- parameter1 : kotlin.String
        // - resultType:
        // --- null
        val context = queries.value(0, null)
        val parameter1 = queries.value(1, null)
        Events().onInit(
          context.castToTypeOfT() /* -> NO NEED TO MAPPING :[android.content.Context] */ ,
          parameter1.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.String] */ 
        )}
      "LifeCycleEvent_Destroy" ->  {
        // com.mars.component.detail.event.EventsWithLambda.destroy
        // - resultType:
        // --- kotlin.Unit
        EventsWithLambda().destroy() /* -> NO NEED TO MAPPING :[kotlin.Unit] */ // com.mars.component.detail.event.Events.onDestroy
        // - parameters:
        // --- context : android.content.Context
        // --- parameter1 : kotlin.String
        // - resultType:
        // --- null
        val context = queries.value(0, null)
        val parameter1 = queries.value(1, null)
        Events().onDestroy(
          context.castToTypeOfT() /* -> NO NEED TO MAPPING :[android.content.Context] */ ,
          parameter1.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.String] */ 
        )}
      else -> { }
    }
  }

  override fun onRoute(
    path: String,
    queries: RubikQueries,
    results: RubikResultGroups
  ) {
    when {
      "doTestGenericity7" == path ->  {
        val data = queries.value(0, null)
        doTestGenericity7(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "testGenericity4" == path ->  {
        testGenericity4().apply {
          results.set(0, RubikResult(this))
        }
      }
      "testGenericity5" == path ->  {
        val lambdaArg0 = queries.value(0, "lambdaArg0")
        testGenericity5(lambdaArg0.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestGenericity6" == path ->  {
        val data = queries.value(0, null)
        doTestGenericity6(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestGenericity1" == path ->  {
        val data = queries.value(0, null)
        doTestGenericity1(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestGenericity2" == path ->  {
        val data = queries.value(0, null)
        doTestGenericity2(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestGenericity3" == path ->  {
        val data = queries.value(0, null)
        doTestGenericity3(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestGenericity8" == path ->  {
        val resultSuccessCallback = queries.value(0, null)
        doTestGenericity8(resultSuccessCallback.castToTypeOfT())
      }
      "doTestGenericity9" == path ->  {
        val resultSuccessCallback = queries.value(0, null)
        doTestGenericity9(resultSuccessCallback.castToTypeOfT())
      }
      "create-test-default-path-task-instance" == path ->  {
        val routeResultTransformer: (Any) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        createTestDefaultPathTaskInstance(routeResultTransformer)
      }
      "doTestDataType1" == path ->  {
        val data = queries.value(0, null)
        doTestDataType1(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType2" == path ->  {
        val data = queries.value(0, null)
        doTestDataType2(data.castToTypeOfT()).apply {
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
      else -> { onRouteExt1(path, queries, results) }
    }
  }

  private fun onRouteExt1(
    path: String,
    queries: RubikQueries,
    results: RubikResultGroups
  ) {
    when {
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
      "doTestDataType8" == path ->  {
        val data = queries.value(0, null)
        doTestDataType8(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType9" == path ->  {
        val data = queries.value(0, null)
        doTestDataType9(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType10" == path ->  {
        val resultSuccessCallback = queries.value(0, null)
        val resultSuccess2Callback = queries.value(1, null)
        doTestDataType10(resultSuccessCallback.castToTypeOfT(), resultSuccess2Callback.castToTypeOfT())
      }
      "doTestDataType11" == path ->  {
        val resultSuccessCallback: (Int, TestNullableBeanCtx) -> Unit = { lambdaArg0, lambdaArg1 ->
          results.set(0, RubikResult(lambdaArg0),RubikResult(lambdaArg1))
        }
        val resultSuccess2Callback: (TestNullableBeanCtx?) -> Unit = { lambdaArg0 ->
          results.set(1, RubikResult(lambdaArg0))
        }
        doTestDataType11(resultSuccessCallback, resultSuccess2Callback)
      }
      "doTestDataType13" == path ->  {
        val data = queries.value(0, null)
        doTestDataType13(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType14" == path ->  {
        val data = queries.value(0, null)
        doTestDataType14(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType15" == path ->  {
        val data = queries.value(0, null)
        doTestDataType15(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType16" == path ->  {
        val data = queries.value(0, null)
        doTestDataType16(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType17" == path ->  {
        val data = queries.value(0, null)
        doTestDataType17(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType18" == path ->  {
        val data = queries.value(0, null)
        doTestDataType18(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType19" == path ->  {
        val data = queries.value(0, null)
        doTestDataType19(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType20" == path ->  {
        val media = queries.value(0, null)
        doTestDataType20(media.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType21" == path ->  {
        val data = queries.value(0, null)
        doTestDataType21(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      else -> { onRouteExt2(path, queries, results) }
    }
  }

  private fun onRouteExt2(
    path: String,
    queries: RubikQueries,
    results: RubikResultGroups
  ) {
    when {
      "doTestDataType22" == path ->  {
        val data = queries.value(0, null)
        doTestDataType22(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType23" == path ->  {
        val data = queries.value(0, null)
        doTestDataType23(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType24" == path ->  {
        val data = queries.value(0, null)
        doTestDataType24(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType25" == path ->  {
        val data = queries.value(0, null)
        doTestDataType25(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType26" == path ->  {
        val data = queries.value(0, null)
        doTestDataType26(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "doTestDataType27" == path ->  {
        val data = queries.value(0, null)
        doTestDataType27(data.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "test-bean/create" == path ->  {
        val d1 = queries.value(0, null)
        val d2 = queries.value(1, null)
        val routeResultTransformer: (TestCreateBeanCtx) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        testBeanCreate(d1.castToTypeOfT(), d2.castToTypeOfT(), routeResultTransformer)
      }
      "do-sth-in-common-instance" == path ->  {
        val objectInstance = queries.value(0, null)
        val routeResultTransformer: (TestDataBeanCtx) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        doSthInCommonInstance(objectInstance.castToTypeOfT(), routeResultTransformer)
      }
      "do-sth-in-common-instance-parameter" == path ->  {
        val objectInstance = queries.value(0, null)
        val v1 = queries.value(1, null)
        val v2 = queries.value(2, null)
        val v3 = queries.value(3, null)
        doSthInCommonInstanceParameter(objectInstance.castToTypeOfT(), v1.castToTypeOfT(), v2.castToTypeOfT(), v3.castToTypeOfT())
      }
      "do-sth-create-common-instance" == path ->  {
        val objectInstance = queries.value(0, null)
        val routeResultTransformer: (TestDataBeanCtx) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        doSthCreateCommonInstance(objectInstance.castToTypeOfT(), routeResultTransformer)
      }
      "get-a-other-common-instance" == path ->  {
        val i = queries.value(0, null)
        val routeResultTransformer: (ObjectTestCtx) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        getAOtherCommonInstance(i.castToTypeOfT(), routeResultTransformer)
      }
      "send-back-common-instance" == path ->  {
        val obj = queries.value(0, null)
        sendBackCommonInstance(obj.castToTypeOfT())
      }
      "get-a-other-common-instance-null" == path ->  {
        val i = queries.value(0, null)
        val routeResultTransformer: ((ObjectTestCtx?) -> Unit)? = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        getAOtherCommonInstanceNull(i.castToTypeOfT(), routeResultTransformer)
      }
      "send-back-common-instance-null" == path ->  {
        val obj = queries.value(0, null)
        sendBackCommonInstanceNull(obj.castToTypeOfT())
      }
      "do-sth-ext" == path ->  {
        val i = queries.value(0, null)
        val s = queries.value(1, null)
        val routeResultTransformer: (Int) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        doSthExt(i.castToTypeOfT(), s.castToTypeOfT(), routeResultTransformer)
      }
      else -> { onRouteExt3(path, queries, results) }
    }
  }

  private fun onRouteExt3(
    path: String,
    queries: RubikQueries,
    results: RubikResultGroups
  ) {
    when {
      "doSthCompanion" == path ->  {
        val i = queries.value(0, null)
        val j = queries.value(1, null)
        val k = queries.value(2, null)
        val routeResultTransformer: (String) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        doSthCompanion(i.castToTypeOfT(), j.castToTypeOfT(), k.castToTypeOfT(), routeResultTransformer)
      }
      "property/companion" == path ->  {
        val routeResultTransformer: (String) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        propertyCompanion(routeResultTransformer)
      }
      "doSthHOFCompanion" == path ->  {
        val lambdaArg0 = queries.value(0, "lambdaArg0")
        val routeResultTransformer: (Int) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        doSthHOFCompanion(lambdaArg0.castToTypeOfT(), routeResultTransformer)
      }
      "2.0/do-sth-hof" == path ->  {
        val lambdaArg0 = queries.value(0, "lambdaArg0")
        val lambdaArg1 = queries.value(1, "lambdaArg1")
        val lambdaArg2 = queries.value(2, "lambdaArg2")
        doSthHof(lambdaArg0.castToTypeOfT(), lambdaArg1.castToTypeOfT(), lambdaArg2.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "1.0/property/property" == path ->  {
        val routeResultTransformer: (String) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        propertyProperty(routeResultTransformer)
      }
      "do-sth" == path ->  {
        doSth()
      }
      "view/get" == path ->  {
        val context = queries.value(0, null)
        val routeResultTransformer: ((View?) -> Unit)? = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        viewGet(context.castToTypeOfT(), routeResultTransformer)
      }
      RubikPath("sth/{id}/a-{name}?code1={code1}&code2={code2}").matching(path) ->  {
        queries.addAll(RubikPath("sth/{id}/a-{name}?code1={code1}&code2={code2}").getParameters(path))
        val id = queries.value(0, null)
        val name = queries.value(1, null)
        val code1 = queries.value(2, null)
        val code2 = queries.value(3, null)
        val routeResultTransformer: (String) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        sthIdAName(id.castToTypeOfT(), name.castToTypeOfT(), code1.castToTypeOfT(), code2.castToTypeOfT(), routeResultTransformer)
      }
      RubikPath("sth-navigation-only/{uri}").matching(path) ->  {
        queries.addAll(RubikPath("sth-navigation-only/{uri}").getParameters(path))
        val uriParameter = queries.value(0, null)
        val routeResultTransformer: (String) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        sthNavigationOnlyUri(uriParameter.castToTypeOfT(), routeResultTransformer)
      }
      "doSthVararg" == path ->  {
        val no = queries.value(0, null)
        val varargString = queries.value(1, null)
        doSthVararg(no.castToTypeOfT(), *varargString.castToTypeOfT())
      }
      "doSthVarargHof" == path ->  {
        val no = queries.value(0, null)
        val varargString = queries.value(1, null)
        doSthVarargHof(no.castToTypeOfT(), *varargString.castToTypeOfT())
      }
      "doSthBean" == path ->  {
        val a1 = queries.value(0, null)
        val routeResultTransformer: (TestListBeanCtx) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        doSthBean(a1.castToTypeOfT(), routeResultTransformer)
      }
      "do-sth-uri-crash" == path ->  {
        val name = queries.value(0, null)
        val code = queries.value(1, null)
        doSthUriCrash(name.castToTypeOfT(), code.castToTypeOfT())
      }
      "do-sth-uri-crash-by-name-code-version" == path ->  {
        val name = queries.value(0, null)
        val code = queries.value(1, null)
        val version = queries.value(2, null)
        doSthUriCrashByNameCodeVersion(name.castToTypeOfT(), code.castToTypeOfT(), version.castToTypeOfT())
      }
      "doSthMappingBean" == path ->  {
        val a1 = queries.value(0, null)
        val routeResultTransformer: (TestDataMappingBeanCtx) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        doSthMappingBean(a1.castToTypeOfT(), routeResultTransformer)
      }
      else -> { onRouteExt4(path, queries, results) }
    }
  }

  private fun onRouteExt4(
    path: String,
    queries: RubikQueries,
    results: RubikResultGroups
  ) {
    when {
      "doSthTestNestDataMappingBean" == path ->  {
        val bean = queries.value(0, null)
        val routeResultTransformer: (TestNestDataMappingBeanCtx) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        doSthTestNestDataMappingBean(bean.castToTypeOfT(), routeResultTransformer)
      }
      "doSthAsyncHOF" == path ->  {
        val hofCallback: (String, TestDataBeanCtx) -> Unit = { lambdaArg0, lambdaArg1 ->
          results.set(0, RubikResult(lambdaArg0),RubikResult(lambdaArg1))
        }
        doSthAsyncHOF(hofCallback)
      }
      "doSthAsyncHOFNullable" == path ->  {
        val hof2xxCallback = queries.value(0, null)
        val hof4xxCallback = queries.value(1, null)
        val hofx1resCallback: ((String, TestDataBeanCtx) -> Unit)? = { lambdaArg0, lambdaArg1 ->
          results.set(0, RubikResult(lambdaArg0),RubikResult(lambdaArg1))
        }
        doSthAsyncHOFNullable(hof2xxCallback.castToTypeOfT(), hof4xxCallback.castToTypeOfT(), hofx1resCallback)
      }
      "doSthAsync2HOF" == path ->  {
        val hofCallback: (String, Int) -> Unit = { lambdaArg0, lambdaArg1 ->
          results.set(0, RubikResult(lambdaArg0),RubikResult(lambdaArg1))
        }
        val hof2Callback: (String, Int) -> Unit = { lambdaArg0, lambdaArg1 ->
          results.set(1, RubikResult(lambdaArg0),RubikResult(lambdaArg1))
        }
        doSthAsync2HOF(hofCallback, hof2Callback)
      }
      "doSthAsyncOpen" == path ->  {
        val uriParameter = queries.value(0, null)
        val onCallCallback: ((String?, Int) -> Unit)? = { lambdaArg0, lambdaArg1 ->
          results.set(0, RubikResult(lambdaArg0),RubikResult(lambdaArg1))
        }
        doSthAsyncOpen(uriParameter.castToTypeOfT(), onCallCallback)
      }
      "do-sth-async-interface" == path ->  {
        val onCallCallback: (String?, Int) -> Unit = { lambdaArg0, lambdaArg1 ->
          results.set(0, RubikResult(lambdaArg0),RubikResult(lambdaArg1))
        }
        doSthAsyncInterface(onCallCallback)
      }
      "do-sth-async-3-interface" == path ->  {
        val onCallCallback: ((String?, Int) -> Unit)? = { lambdaArg0, lambdaArg1 ->
          results.set(0, RubikResult(lambdaArg0),RubikResult(lambdaArg1))
        }
        val onCall1Callback: (String?, Int) -> Unit = { lambdaArg0, lambdaArg1 ->
          results.set(1, RubikResult(lambdaArg0),RubikResult(lambdaArg1))
        }
        val onCall2Callback: (TestDataBeanCtx?) -> Unit = { lambdaArg0 ->
          results.set(2, RubikResult(lambdaArg0))
        }
        doSthAsync3Interface(onCallCallback, onCall1Callback, onCall2Callback)
      }
      "do-sth-async-interface-multi-func" == path ->  {
        val startCallback: (String?, Int) -> Unit = { lambdaArg0, lambdaArg1 ->
          results.set(0, RubikResult(lambdaArg0),RubikResult(lambdaArg1))
        }
        val dataCallback: (TestDataBeanCtx?) -> Unit = { lambdaArg0 ->
          results.set(1, RubikResult(lambdaArg0))
        }
        val stopCallback: (String?, Int) -> Unit = { lambdaArg0, lambdaArg1 ->
          results.set(2, RubikResult(lambdaArg0),RubikResult(lambdaArg1))
        }
        doSthAsyncInterfaceMultiFunc(startCallback, dataCallback, stopCallback)
      }
      "doSthHOFTop" == path ->  {
        val lambdaArg0 = queries.value(0, "lambdaArg0")
        val routeResultTransformer: (Unit) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        doSthHOFTop(lambdaArg0.castToTypeOfT(), routeResultTransformer)
      }
      "property/top" == path ->  {
        val routeResultTransformer: (String) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        propertyTop(routeResultTransformer)
      }
      "doSthTop" == path ->  {
        val ints = queries.value(0, null)
        val li = queries.value(1, null)
        val strings = queries.value(2, null)
        val ls = queries.value(3, null)
        val beans = queries.value(4, null)
        val lb = queries.value(5, null)
        val routeResultTransformer: (List<TestDataBeanCtx>) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        doSthTop(ints.castToTypeOfT(), li.castToTypeOfT(), strings.castToTypeOfT(), ls.castToTypeOfT(), beans.castToTypeOfT(), lb.castToTypeOfT(), routeResultTransformer)
      }
      "getFilesMeta" == path ->  {
        val context = queries.value(0, null)
        val pathParameter = queries.value(1, null)
        val onResultCallback: (Long, String?) -> Unit = { lambdaArg0, lambdaArg1 ->
          results.set(0, RubikResult(lambdaArg0),RubikResult(lambdaArg1))
        }
        getFilesMeta(context.castToTypeOfT(), pathParameter.castToTypeOfT(), onResultCallback)
      }
      "enterprise/business/allocateTicket" == path ->  {
        val context = queries.value(0, null)
        val shareId = queries.value(1, null)
        val count = queries.value(2, null)
        val packetType = queries.value(3, null)
        val routeResultTransformer: ((LiveData<List<String>>?) -> Unit)? = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        enterpriseBusinessAllocateTicket(context.castToTypeOfT(), shareId.castToTypeOfT(), count.castToTypeOfT(), packetType.castToTypeOfT(), routeResultTransformer)
      }
      "do-sth-provide-instance-by-func" == path ->  {
        doSthProvideInstanceByFunc()
      }
      "do-sth-provide-instance-by-func2" == path ->  {
        doSthProvideInstanceByFunc2()
      }
      else -> { onRouteExt5(path, queries, results) }
    }
  }

  private fun onRouteExt5(
    path: String,
    queries: RubikQueries,
    results: RubikResultGroups
  ) {
    when {
      "do-sth-provide-instance-by-parameter-func" == path ->  {
        val rubikInstanceValue = queries.value(0, null)
        val rubikInstanceV0 = queries.value(1, null)
        val v1 = queries.value(2, null)
        val v2 = queries.value(3, null)
        val v3 = queries.value(4, null)
        doSthProvideInstanceByParameterFunc(rubikInstanceValue.castToTypeOfT(), rubikInstanceV0.castToTypeOfT(), v1.castToTypeOfT(), v2.castToTypeOfT(), v3.castToTypeOfT())
      }
      "do-sth-provide-instance-by-constructor" == path ->  {
        val rubikInstanceValue = queries.value(0, null)
        val v1 = queries.value(1, null)
        val v2 = queries.value(2, null)
        val v3 = queries.value(3, null)
        doSthProvideInstanceByConstructor(rubikInstanceValue.castToTypeOfT(), v1.castToTypeOfT(), v2.castToTypeOfT(), v3.castToTypeOfT())
      }
      "doSthResultReceiver" == path ->  {
        val resultParameter = queries.value(0, null)
        val routeResultTransformer: (ResultReceiver) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        doSthResultReceiver(resultParameter.castToTypeOfT(), routeResultTransformer)
      }
      "live-data/get" == path ->  {
        val routeResultTransformer: (LiveData<String>) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        liveDataGet(routeResultTransformer)
      }
      "live-data-bean/get" == path ->  {
        val routeResultTransformer: (LiveData<TestDataBeanCtx>) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        liveDataBeanGet(routeResultTransformer)
      }
      "live-data-bean-list/get" == path ->  {
        val routeResultTransformer: (LiveData<List<TestListBeanCtx?>>) -> Unit = { lambdaArg0 ->
          results.set(0, RubikResult(lambdaArg0))
        }
        liveDataBeanListGet(routeResultTransformer)
      }
      "do-sth-callback-object" == path ->  {
        val callback = queries.value(0, null)
        doSthCallbackObject(callback.castToTypeOfT())
      }
      "fragment/page1" == path ->  {
        fragmentPage1().apply {
          results.set(0, RubikResult(this))
        }
      }
      "test-name-string" == path ->  {
        testNameString().apply {
          results.set(0, RubikResult(this))
        }
      }
      "test-name" == path ->  {
        testName()
      }
      "test_name_up" == path ->  {
        testNameUp()
      }
      "object/get/do-sth-in-common-ins" == path ->  {
        val objectInstance = queries.value(0, null)
        val v1 = queries.value(1, null)
        val v2 = queries.value(2, null)
        val v3 = queries.value(3, null)
        objectGetDoSthInCommonIns(objectInstance.castToTypeOfT(), v1.castToTypeOfT(), v2.castToTypeOfT(), v3.castToTypeOfT())
      }
      "get-a-lot-of-common-instance" == path ->  {
        val i = queries.value(0, null)
        getALotOfCommonInstance(i.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "send-back-a-lot-of-common-instance" == path ->  {
        val objs = queries.value(0, null)
        sendBackALotOfCommonInstance(objs.castToTypeOfT())
      }
      "get-a-lot-of-common-instance-null" == path ->  {
        val i = queries.value(0, null)
        getALotOfCommonInstanceNull(i.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      else -> { onRouteExt6(path, queries, results) }
    }
  }

  private fun onRouteExt6(
    path: String,
    queries: RubikQueries,
    results: RubikResultGroups
  ) {
    when {
      "send-back-a-lot-of-common-instance-null" == path ->  {
        val objs = queries.value(0, null)
        sendBackALotOfCommonInstanceNull(objs.castToTypeOfT())
      }
      "api/bigdata/json-array" == path ->  {
        val array = queries.value(0, null)
        apiBigdataJsonArray(array.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "api/bigdata/parcel-array" == path ->  {
        val array = queries.value(0, null)
        apiBigdataParcelArray(array.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "api/bigdata/lib-array" == path ->  {
        val array = queries.value(0, null)
        apiBigdataLibArray(array.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "api/bigdata/json-list" == path ->  {
        val list = queries.value(0, null)
        apiBigdataJsonList(list.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "api/bigdata/parcel-list" == path ->  {
        val list = queries.value(0, null)
        apiBigdataParcelList(list.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "api/bigdata/lib-list" == path ->  {
        val list = queries.value(0, null)
        apiBigdataLibList(list.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "api/bigdata/parcel-arraylist" == path ->  {
        val list = queries.value(0, null)
        apiBigdataParcelArraylist(list.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "api/serialization/parcel-bean" == path ->  {
        val parcelBean = queries.value(0, null)
        apiSerializationParcelBean(parcelBean.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "api/serialization/parcel-array" == path ->  {
        val parcels = queries.value(0, null)
        apiSerializationParcelArray(parcels.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "api/serialization/parcel-list" == path ->  {
        val parcels = queries.value(0, null)
        apiSerializationParcelList(parcels.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "api/serialization/Serializable-bean" == path ->  {
        val serializableBean = queries.value(0, null)
        apiSerializationSerializableBean(serializableBean.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "create-object-test-instance" == path ->  {
        val value = queries.value(0, null)
        createObjectTestInstance(value.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "create-object-test-create-instance" == path ->  {
        val v1 = queries.value(0, null)
        val v2 = queries.value(1, null)
        createObjectTestCreateInstance(v1.castToTypeOfT(), v2.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      "create-object-test-create-instance-by-v1-v2-v3-v4" == path ->  {
        val v1 = queries.value(0, null)
        val v2 = queries.value(1, null)
        val v3 = queries.value(2, null)
        val v4 = queries.value(3, null)
        createObjectTestCreateInstanceByV1V2V3V4(v1.castToTypeOfT(), v2.castToTypeOfT(), v3.castToTypeOfT(), v4.castToTypeOfT()).apply {
          results.set(0, RubikResult(this))
        }
      }
      else -> { onRouteExt7(path, queries, results) }
    }
  }

  private fun onRouteExt7(
    path: String,
    queries: RubikQueries,
    results: RubikResultGroups
  ) {
    when {
      RubikPath("activity/page2/{key_str1}/{key_str2}/{key_str3}").matching(path) ->  {
        // com.mars.component.detail.ui.SecondPageActivity
        // - parameters:
        // --- key_str1 : kotlin.String?
        // --- key_str2 : kotlin.String?
        // --- key_str3 : kotlin.String?
        RubikLauncher().launch(SecondPageActivity::class.java, queries, RubikPath("activity/page2/{key_str1}/{key_str2}/{key_str3}").getParameters(path), results)
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
        RubikLauncher().launch(FirstPageActivity::class.java, queries, null, results)
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
        RubikLauncher().launch(FirstPageActivity::class.java, queries, null, results)
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
        RubikLauncher().launch(FirstPageActivity::class.java, queries, null, results)
      }
      else -> { throw com.rubik.route.exception.BadPathOrVersionException(path) }
    }
  }

  override fun doTestGenericity7(data: Array<Long?>?): Array<Long?>? {
    // com.mars.component.detail.test.TestGenericityTask.Companion.doTestGenericity7
    // - parameters:
    // --- data : kotlin.Array<kotlin.Long?>?
    // - resultType:
    // --- kotlin.Array<kotlin.Long?>?
    return TestGenericityTask.Companion.doTestGenericity7(
      data /* -> NO NEED TO MAPPING :[kotlin.Array<kotlin.Long?>?] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.Array<kotlin.Long?>?] */ 
  }

  override fun testGenericity4(): List<String?>? {
    // com.mars.component.detail.test.TestGenericityTask.testGenericity4
    // - resultType:
    // --- kotlin.collections.List<kotlin.String?>
    return TestGenericityTask().testGenericity4 /* -> NO NEED TO MAPPING :[kotlin.collections.List<kotlin.String?>] */ 
  }

  override fun testGenericity5(lambdaArg0: List<String?>?): List<String?>? {
    // com.mars.component.detail.test.TestGenericityTask.testGenericity5
    // - parameters:
    // --- lambdaArg0 : kotlin.collections.List<kotlin.String?>?
    // - resultType:
    // --- kotlin.collections.List<kotlin.String?>?
    return TestGenericityTask().testGenericity5(
      lambdaArg0 /* -> NO NEED TO MAPPING :[kotlin.collections.List<kotlin.String?>?] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.collections.List<kotlin.String?>?] */ 
  }

  override fun doTestGenericity6(data: Array<TestNullableBeanCtx?>?): Array<TestNullableBeanCtx?>? {
    // com.mars.component.detail.test.TestGenericityTask.doTestGenericity6
    // - parameters:
    // --- data : kotlin.Array<com.mars.component.detail.value.TestNullableBean?>?
    // - resultType:
    // --- kotlin.Array<com.mars.component.detail.value.TestNullableBean?>?
    return TestGenericityTask().doTestGenericity6(
      data?.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.Array<com.mars.component.detail.value.TestNullableBean?>?] */ 
    )?.toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.Array<rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean?>?] */ 
  }

  override fun doTestGenericity1(data: List<TestNullableBeanCtx?>?): List<TestNullableBeanCtx?>? {
    // com.mars.component.detail.test.TestGenericityTaskKt.doTestGenericity1
    // - parameters:
    // --- data : kotlin.collections.List<com.mars.component.detail.value.TestNullableBean?>?
    // - resultType:
    // --- kotlin.collections.List<com.mars.component.detail.value.TestNullableBean?>?
    return com.mars.component.detail.test.doTestGenericity1(
      data?.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.List<com.mars.component.detail.value.TestNullableBean?>?] */ 
    )?.toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean?>?] */ 
  }

  override fun doTestGenericity2(data: LiveData<TestNullableBeanCtx?>?):
      LiveData<TestNullableBeanCtx?>? {
    // com.mars.component.detail.test.TestGenericityTaskKt.doTestGenericity2
    // - parameters:
    // --- data : androidx.lifecycle.LiveData<com.mars.component.detail.value.TestNullableBean?>?
    // - resultType:
    // --- androidx.lifecycle.LiveData<com.mars.component.detail.value.TestNullableBean?>?
    return com.mars.component.detail.test.doTestGenericity2(
      data?.toTypeOfT() /* -> TO ORIGINAL TYPE :[androidx.lifecycle.LiveData<com.mars.component.detail.value.TestNullableBean?>?] */ 
    )?.toTypeOfT() /* -> TO CONTEXT TYPE :[androidx.lifecycle.LiveData<rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean?>?] */ 
  }

  override fun doTestGenericity3(data: Map<String, Long?>): Map<String, Long?>? {
    // com.mars.component.detail.test.TestGenericityTaskKt.doTestGenericity3
    // - parameters:
    // --- data : kotlin.collections.Map<kotlin.String, kotlin.Long?>
    // - resultType:
    // --- kotlin.collections.Map<kotlin.String, kotlin.Long?>
    return com.mars.component.detail.test.doTestGenericity3(
      data /* -> NO NEED TO MAPPING :[kotlin.collections.Map<kotlin.String, kotlin.Long?>] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.collections.Map<kotlin.String, kotlin.Long?>] */ 
  }

  override fun doTestGenericity8(resultSuccessCallback: Function1<ArrayList<String>, Unit>) {
    // com.mars.component.detail.test.TestGenericityTaskKt.doTestGenericity8
    // - parameters:
    // --- resultSuccess : kotlin.Function1<kotlin.collections.ArrayList<kotlin.String>, kotlin.Unit>
    // - resultType:
    // --- null
    com.mars.component.detail.test.doTestGenericity8(
      resultSuccessCallback /* -> NO NEED TO MAPPING :[kotlin.Function1<kotlin.collections.ArrayList<kotlin.String>, kotlin.Unit>] */ 
    )
  }

  override fun doTestGenericity9(resultSuccessCallback: Function1<List<String>, Unit>) {
    // com.mars.component.detail.test.TestGenericityTaskKt.doTestGenericity9
    // - parameters:
    // --- resultSuccess : kotlin.Function1<kotlin.collections.List<kotlin.String>, kotlin.Unit>
    // - resultType:
    // --- null
    com.mars.component.detail.test.doTestGenericity9(
      resultSuccessCallback /* -> NO NEED TO MAPPING :[kotlin.Function1<kotlin.collections.List<kotlin.String>, kotlin.Unit>] */ 
    )
  }

  override fun createTestDefaultPathTaskInstance(routeResultTransformer: (Any) -> Unit) {
    // com.mars.component.detail.test.TestDefaultPathTask
    // - resultType:
    // --- com.mars.component.detail.test.TestDefaultPathTask
    routeResultTransformer(TestDefaultPathTask() /* -> NO NEED TO MAPPING :[com.mars.component.detail.test.TestDefaultPathTask] */ )
  }

  override fun doTestDataType1(data: Context?): Context? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType1
    // - parameters:
    // --- data : android.content.Context?
    // - resultType:
    // --- android.content.Context?
    return TestDataTypeTask().doTestDataType1(
      data /* -> NO NEED TO MAPPING :[android.content.Context?] */ 
    ) /* -> NO NEED TO MAPPING :[android.content.Context?] */ 
  }

  override fun doTestDataType2(data: Int?): Int? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType2
    // - parameters:
    // --- data : kotlin.Int?
    // - resultType:
    // --- kotlin.Int?
    return TestDataTypeTask().doTestDataType2(
      data /* -> NO NEED TO MAPPING :[kotlin.Int?] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.Int?] */ 
  }

  override fun doTestDataType3(data: String?): String? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType3
    // - parameters:
    // --- data : kotlin.String?
    // - resultType:
    // --- kotlin.String?
    return TestDataTypeTask().doTestDataType3(
      data /* -> NO NEED TO MAPPING :[kotlin.String?] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.String?] */ 
  }

  override fun doTestDataType4(data: TestNullableBeanCtx?): TestNullableBeanCtx? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType4
    // - parameters:
    // --- data : com.mars.component.detail.value.TestNullableBean?
    // - resultType:
    // --- com.mars.component.detail.value.TestNullableBean?
    return TestDataTypeTask().doTestDataType4(
      data?.toTypeOfT() /* -> TO ORIGINAL TYPE :[com.mars.component.detail.value.TestNullableBean?] */ 
    )?.toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean?] */ 
  }

  override fun doTestDataType5(data: List<String>?): List<String>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType5
    // - parameters:
    // --- data : kotlin.collections.List<kotlin.String>?
    // - resultType:
    // --- kotlin.collections.List<kotlin.String>?
    return TestDataTypeTask().doTestDataType5(
      data /* -> NO NEED TO MAPPING :[kotlin.collections.List<kotlin.String>?] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.collections.List<kotlin.String>?] */ 
  }

  override fun doTestDataType6(data: Array<Long>?): Array<Long>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType6
    // - parameters:
    // --- data : kotlin.Array<kotlin.Long>?
    // - resultType:
    // --- kotlin.Array<kotlin.Long>?
    return TestDataTypeTask().doTestDataType6(
      data /* -> NO NEED TO MAPPING :[kotlin.Array<kotlin.Long>?] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.Array<kotlin.Long>?] */ 
  }

  override fun doTestDataType7(data: LongArray?): LongArray? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType7
    // - parameters:
    // --- data : kotlin.LongArray?
    // - resultType:
    // --- kotlin.LongArray?
    return TestDataTypeTask().doTestDataType7(
      data /* -> NO NEED TO MAPPING :[kotlin.LongArray?] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.LongArray?] */ 
  }

  override fun doTestDataType8(data: IntArray?): IntArray? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType8
    // - parameters:
    // --- data : kotlin.IntArray?
    // - resultType:
    // --- kotlin.IntArray?
    return TestDataTypeTask().doTestDataType8(
      data /* -> NO NEED TO MAPPING :[kotlin.IntArray?] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.IntArray?] */ 
  }

  override fun doTestDataType9(data: BooleanArray?): BooleanArray? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType9
    // - parameters:
    // --- data : kotlin.BooleanArray?
    // - resultType:
    // --- kotlin.BooleanArray?
    return TestDataTypeTask().doTestDataType9(
      data /* -> NO NEED TO MAPPING :[kotlin.BooleanArray?] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.BooleanArray?] */ 
  }

  override fun doTestDataType10(resultSuccessCallback: Function1<Int, Unit>,
      resultSuccess2Callback: Function1<String, Unit>) {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType10
    // - parameters:
    // --- resultSuccess : kotlin.Function1<kotlin.Int, kotlin.Unit>
    // --- resultSuccess2 : kotlin.Function1<kotlin.String, kotlin.Unit>
    // - resultType:
    // --- null
    TestDataTypeTask().doTestDataType10(
      resultSuccessCallback /* -> NO NEED TO MAPPING :[kotlin.Function1<kotlin.Int, kotlin.Unit>] */ ,
      resultSuccess2Callback /* -> NO NEED TO MAPPING :[kotlin.Function1<kotlin.String, kotlin.Unit>] */ 
    )
  }

  override fun doTestDataType11(resultSuccessCallback: (Int, TestNullableBeanCtx) -> Unit,
      resultSuccess2Callback: (TestNullableBeanCtx?) -> Unit) {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType11
    // - parameters:
    // --- resultSuccess : kotlin.Function2<kotlin.Int, com.mars.component.detail.value.TestNullableBean, kotlin.Unit>
    // --- resultSuccess2 : kotlin.Function1<com.mars.component.detail.value.TestNullableBean?, kotlin.Unit>
    // - resultType:
    // --- null
    val resultSuccessCallbackCallbackTransformer: (Any?, Any?) -> Unit = { lambdaArg0, lambdaArg1 -> 
      resultSuccessCallback(
        lambdaArg0.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Int] */ ,
        lambdaArg1.toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean] */ 
      )}
    val resultSuccess2CallbackCallbackTransformer: (Any?) -> Unit = { lambdaArg0 -> 
      resultSuccess2Callback(
        lambdaArg0?.toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean?] */ 
      )}
    TestDataTypeTask().doTestDataType11(
      resultSuccessCallbackCallbackTransformer,
      resultSuccess2CallbackCallbackTransformer
    )
  }

  override fun doTestDataType13(data: List<Int?>): List<Int?>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType13
    // - parameters:
    // --- data : kotlin.collections.List<kotlin.Int?>
    // - resultType:
    // --- kotlin.collections.List<kotlin.Int?>
    return TestDataTypeTask().doTestDataType13(
      data /* -> NO NEED TO MAPPING :[kotlin.collections.List<kotlin.Int?>] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.collections.List<kotlin.Int?>] */ 
  }

  override fun doTestDataType14(data: ArrayList<Int?>): ArrayList<Int?>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType14
    // - parameters:
    // --- data : kotlin.collections.ArrayList<kotlin.Int?>
    // - resultType:
    // --- kotlin.collections.ArrayList<kotlin.Int?>
    return TestDataTypeTask().doTestDataType14(
      data /* -> NO NEED TO MAPPING :[kotlin.collections.ArrayList<kotlin.Int?>] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.collections.ArrayList<kotlin.Int?>] */ 
  }

  override fun doTestDataType15(data: List<TestNullableBeanCtx?>): List<TestNullableBeanCtx?>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType15
    // - parameters:
    // --- data : kotlin.collections.List<com.mars.component.detail.value.TestNullableBean?>
    // - resultType:
    // --- kotlin.collections.List<com.mars.component.detail.value.TestNullableBean?>
    return TestDataTypeTask().doTestDataType15(
      data.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.List<com.mars.component.detail.value.TestNullableBean?>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean?>] */ 
  }

  override fun doTestDataType16(data: ArrayList<TestNullableBeanCtx?>):
      ArrayList<TestNullableBeanCtx?>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType16
    // - parameters:
    // --- data : kotlin.collections.ArrayList<com.mars.component.detail.value.TestNullableBean?>
    // - resultType:
    // --- kotlin.collections.ArrayList<com.mars.component.detail.value.TestNullableBean?>
    return TestDataTypeTask().doTestDataType16(
      data.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.ArrayList<com.mars.component.detail.value.TestNullableBean?>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.ArrayList<rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean?>] */ 
  }

  override fun doTestDataType17(data: List<String?>): List<String?>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType17
    // - parameters:
    // --- data : kotlin.collections.List<kotlin.String?>
    // - resultType:
    // --- kotlin.collections.List<kotlin.String?>
    return TestDataTypeTask().doTestDataType17(
      data /* -> NO NEED TO MAPPING :[kotlin.collections.List<kotlin.String?>] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.collections.List<kotlin.String?>] */ 
  }

  override fun doTestDataType18(data: ArrayList<String?>): ArrayList<String?>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType18
    // - parameters:
    // --- data : kotlin.collections.ArrayList<kotlin.String?>
    // - resultType:
    // --- kotlin.collections.ArrayList<kotlin.String?>
    return TestDataTypeTask().doTestDataType18(
      data /* -> NO NEED TO MAPPING :[kotlin.collections.ArrayList<kotlin.String?>] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.collections.ArrayList<kotlin.String?>] */ 
  }

  override fun doTestDataType19(data: ArrayList<Pair<String?, Int?>>): ArrayList<Pair<String?,
      Int?>>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType19
    // - parameters:
    // --- data : kotlin.collections.ArrayList<kotlin.Pair<kotlin.String?, kotlin.Int?>>
    // - resultType:
    // --- kotlin.collections.ArrayList<kotlin.Pair<kotlin.String?, kotlin.Int?>>
    return TestDataTypeTask().doTestDataType19(
      data /* -> NO NEED TO MAPPING :[kotlin.collections.ArrayList<kotlin.Pair<kotlin.String?, kotlin.Int?>>] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.collections.ArrayList<kotlin.Pair<kotlin.String?, kotlin.Int?>>] */ 
  }

  override fun doTestDataType20(media: Pair<Long?, String?>): List<String>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType20
    // - parameters:
    // --- media : kotlin.Pair<kotlin.Long?, kotlin.String?>
    // - resultType:
    // --- kotlin.collections.List<kotlin.String>?
    return TestDataTypeTask().doTestDataType20(
      media /* -> NO NEED TO MAPPING :[kotlin.Pair<kotlin.Long?, kotlin.String?>] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.collections.List<kotlin.String>?] */ 
  }

  override fun doTestDataType21(data: List<TestNullableBeanCtx>): List<TestNullableBeanCtx>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType21
    // - parameters:
    // --- data : kotlin.collections.List<com.mars.component.detail.value.TestNullableBean>
    // - resultType:
    // --- kotlin.collections.List<com.mars.component.detail.value.TestNullableBean>
    return TestDataTypeTask().doTestDataType21(
      data.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.List<com.mars.component.detail.value.TestNullableBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean>] */ 
  }

  override fun doTestDataType22(data: List<TestNullableBeanCtx>): List<TestNullableBeanCtx>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType22
    // - parameters:
    // --- data : kotlin.collections.List<com.mars.component.detail.value.TestNullableBean>
    // - resultType:
    // --- kotlin.collections.List<com.mars.component.detail.value.TestNullableBean>
    return TestDataTypeTask().doTestDataType22(
      data.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.List<com.mars.component.detail.value.TestNullableBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean>] */ 
  }

  override fun doTestDataType23(data: Array<TestNullableBeanCtx>): Array<TestNullableBeanCtx>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType23
    // - parameters:
    // --- data : kotlin.Array<com.mars.component.detail.value.TestNullableBean>
    // - resultType:
    // --- kotlin.Array<com.mars.component.detail.value.TestNullableBean>
    return TestDataTypeTask().doTestDataType23(
      data.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.Array<com.mars.component.detail.value.TestNullableBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.Array<rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean>] */ 
  }

  override fun doTestDataType24(data: Map<String, TestNullableBeanCtx>): Map<String,
      TestNullableBeanCtx>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType24
    // - parameters:
    // --- data : kotlin.collections.Map<kotlin.String, com.mars.component.detail.value.TestNullableBean>
    // - resultType:
    // --- kotlin.collections.Map<kotlin.String, com.mars.component.detail.value.TestNullableBean>
    return TestDataTypeTask().doTestDataType24(
      data.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.Map<kotlin.String, com.mars.component.detail.value.TestNullableBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.Map<kotlin.String, rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean>] */ 
  }

  override fun doTestDataType25(data: Map<String, TestNullableBeanCtx>): Map<String,
      TestNullableBeanCtx>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType25
    // - parameters:
    // --- data : kotlin.collections.Map<kotlin.String, com.mars.component.detail.value.TestNullableBean>
    // - resultType:
    // --- kotlin.collections.Map<kotlin.String, com.mars.component.detail.value.TestNullableBean>
    return TestDataTypeTask().doTestDataType25(
      data.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.Map<kotlin.String, com.mars.component.detail.value.TestNullableBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.Map<kotlin.String, rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean>] */ 
  }

  override fun doTestDataType26(data: Set<TestNullableBeanCtx>): Set<TestNullableBeanCtx>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType26
    // - parameters:
    // --- data : kotlin.collections.Set<com.mars.component.detail.value.TestNullableBean>
    // - resultType:
    // --- kotlin.collections.Set<com.mars.component.detail.value.TestNullableBean>
    return TestDataTypeTask().doTestDataType26(
      data.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.Set<com.mars.component.detail.value.TestNullableBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.Set<rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean>] */ 
  }

  override fun doTestDataType27(data: Set<TestNullableBeanCtx>): Set<TestNullableBeanCtx>? {
    // com.mars.component.detail.test.TestDataTypeTask.doTestDataType27
    // - parameters:
    // --- data : kotlin.collections.Set<com.mars.component.detail.value.TestNullableBean>
    // - resultType:
    // --- kotlin.collections.Set<com.mars.component.detail.value.TestNullableBean>
    return TestDataTypeTask().doTestDataType27(
      data.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.Set<com.mars.component.detail.value.TestNullableBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.Set<rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean>] */ 
  }

  override fun testBeanCreate(
    d1: Int?,
    d2: String?,
    routeResultTransformer: (TestCreateBeanCtx) -> Unit
  ) {
    // com.mars.component.detail.value.TestCreateBean
    // - parameters:
    // --- d1 : kotlin.Int?
    // --- d2 : kotlin.String?
    // - resultType:
    // --- com.mars.component.detail.value.TestCreateBean
    routeResultTransformer(TestCreateBeanOrg(
      d1 /* -> NO NEED TO MAPPING :[kotlin.Int?] */ ,
      d2 /* -> NO NEED TO MAPPING :[kotlin.String?] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestCreateBean] */ )
  }

  override fun doSthInCommonInstance(objectInstance: ObjectTestCtx,
      routeResultTransformer: (TestDataBeanCtx) -> Unit) {
    // com.mars.component.detail.objekt.ObjectTest.doSthInObject
    // - resultType:
    // --- com.mars.component.detail.value.TestDataBean
    routeResultTransformer(objectInstance.mapToType(toObjectTestOrgMapping) /* -> TO ORIGINAL TYPE :[com.mars.component.detail.objekt.ObjectTest] */ .doSthInObject().toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean] */ )
  }

  override fun doSthInCommonInstanceParameter(
    objectInstance: ObjectTestCtx,
    v1: String,
    v2: Int?,
    v3: Int?
  ) {
    // com.mars.component.detail.objekt.ObjectTest.doSthInObject2
    // - parameters:
    // --- v1 : kotlin.String
    // --- v2 : kotlin.Int?
    // --- v3 : kotlin.Int?
    // - resultType:
    // --- null
    objectInstance.mapToType(toObjectTestOrgMapping) /* -> TO ORIGINAL TYPE :[com.mars.component.detail.objekt.ObjectTest] */ .doSthInObject2(
      v1 /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      v2 /* -> NO NEED TO MAPPING :[kotlin.Int?] */ ,
      v3 /* -> NO NEED TO MAPPING :[kotlin.Int?] */ 
    )
  }

  override fun doSthCreateCommonInstance(objectInstance: ObjectTestCreateCtx,
      routeResultTransformer: (TestDataBeanCtx) -> Unit) {
    // com.mars.component.detail.objekt.ObjectTestCreate.doSthInCreateObject
    // - resultType:
    // --- com.mars.component.detail.value.TestDataBean
    routeResultTransformer(objectInstance.mapToType(toObjectTestCreateOrgMapping) /* -> TO ORIGINAL TYPE :[com.mars.component.detail.objekt.ObjectTestCreate] */ .doSthInCreateObject().toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean] */ )
  }

  override fun getAOtherCommonInstance(i: Int, routeResultTransformer: (ObjectTestCtx) -> Unit) {
    // com.mars.component.detail.objekt.ObjectTestKt.getAOtherObjectTest
    // - parameters:
    // --- i : kotlin.Int
    // - resultType:
    // --- com.mars.component.detail.objekt.ObjectTest
    routeResultTransformer(com.mars.component.detail.objekt.getAOtherObjectTest(
      i /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
    ).mapToType(toObjectTestCtxMapping) /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.objekt.ObjectTest] */ )
  }

  override fun sendBackCommonInstance(obj: ObjectTestCtx) {
    // com.mars.component.detail.objekt.ObjectTestKt.senBackObjectTest
    // - parameters:
    // --- obj : com.mars.component.detail.objekt.ObjectTest
    // - resultType:
    // --- null
    com.mars.component.detail.objekt.senBackObjectTest(
      obj.mapToType(toObjectTestOrgMapping) /* -> TO ORIGINAL TYPE :[com.mars.component.detail.objekt.ObjectTest] */ 
    )
  }

  override fun getAOtherCommonInstanceNull(i: Int, routeResultTransformer: ((ObjectTestCtx?) ->
      Unit)?) {
    // com.mars.component.detail.objekt.ObjectTestKt.getAOtherObjectTestNull
    // - parameters:
    // --- i : kotlin.Int
    // - resultType:
    // --- com.mars.component.detail.objekt.ObjectTest?
    if (null != routeResultTransformer) {
      routeResultTransformer(com.mars.component.detail.objekt.getAOtherObjectTestNull(
        i /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
      )?.mapToType(toNullableObjectTestCtxMapping) /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.objekt.ObjectTest?] */ )}
  }

  override fun sendBackCommonInstanceNull(obj: ObjectTestCtx?) {
    // com.mars.component.detail.objekt.ObjectTestKt.senBackObjectTestNull
    // - parameters:
    // --- obj : com.mars.component.detail.objekt.ObjectTest?
    // - resultType:
    // --- null
    com.mars.component.detail.objekt.senBackObjectTestNull(
      obj?.mapToType(toNullableObjectTestOrgMapping) /* -> TO ORIGINAL TYPE :[com.mars.component.detail.objekt.ObjectTest?] */ 
    )
  }

  override fun doSthExt(
    i: Int,
    s: String,
    routeResultTransformer: (Int) -> Unit
  ) {
    // com.mars.component.detail.api.ApisExtKt.doSthExt
    // - parameters:
    // --- s : kotlin.String
    // --- i : kotlin.Int
    // - resultType:
    // --- kotlin.Int
    routeResultTransformer(com.mars.component.detail.api.doSthExt(
      s /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      i /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.Int] */ )
  }

  override fun doSthCompanion(
    i: Int,
    j: String,
    k: Long,
    routeResultTransformer: (String) -> Unit
  ) {
    // com.mars.component.detail.api.AipsInCompanion.DetailCompanion.doSthCompanion
    // - parameters:
    // --- i : kotlin.Int
    // --- j : kotlin.String
    // --- k : kotlin.Long
    // - resultType:
    // --- kotlin.String
    routeResultTransformer(AipsInCompanion.DetailCompanion.doSthCompanion(
      i /* -> NO NEED TO MAPPING :[kotlin.Int] */ ,
      j /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      k /* -> NO NEED TO MAPPING :[kotlin.Long] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.String] */ )
  }

  override fun propertyCompanion(routeResultTransformer: (String) -> Unit) {
    // com.mars.component.detail.api.AipsInCompanion.propertyCompanion
    // - resultType:
    // --- kotlin.String
    routeResultTransformer(com.mars.component.detail.api.AipsInCompanion.propertyCompanion /* -> NO NEED TO MAPPING :[kotlin.String] */ )
  }

  override fun doSthHOFCompanion(lambdaArg0: Int, routeResultTransformer: (Int) -> Unit) {
    // com.mars.component.detail.api.AipsInCompanion.doSthHOFCompanion
    // - parameters:
    // --- lambdaArg0 : kotlin.Int
    // - resultType:
    // --- kotlin.Int
    routeResultTransformer(com.mars.component.detail.api.AipsInCompanion.doSthHOFCompanion(
      lambdaArg0 /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.Int] */ )
  }

  override fun doSthHof(
    lambdaArg0: Int,
    lambdaArg1: String,
    lambdaArg2: TestDataBeanCtx
  ): Int? {
    // com.mars.component.detail.api.Apis.doSthHOF
    // - parameters:
    // --- lambdaArg0 : kotlin.Int
    // --- lambdaArg1 : kotlin.String
    // --- lambdaArg2 : com.mars.component.detail.value.TestDataBean
    // - resultType:
    // --- kotlin.Int
    return Apis().doSthHOF(
      lambdaArg0 /* -> NO NEED TO MAPPING :[kotlin.Int] */ ,
      lambdaArg1 /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      lambdaArg2.toTypeOfT() /* -> TO ORIGINAL TYPE :[com.mars.component.detail.value.TestDataBean] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
  }

  override fun propertyProperty(routeResultTransformer: (String) -> Unit) {
    // com.mars.component.detail.api.Apis.property
    // - resultType:
    // --- kotlin.String
    routeResultTransformer(Apis().property /* -> NO NEED TO MAPPING :[kotlin.String] */ )
  }

  override fun doSth() {
    // com.mars.component.detail.api.Apis.doSth
    // - resultType:
    // --- null
    Apis().doSth()
  }

  override fun viewGet(context: Context, routeResultTransformer: ((View?) -> Unit)?) {
    // com.mars.component.detail.api.Apis.getView
    // - parameters:
    // --- context : android.content.Context
    // - resultType:
    // --- android.view.View?
    if (null != routeResultTransformer) {
      routeResultTransformer(Apis().getView(
        context /* -> NO NEED TO MAPPING :[android.content.Context] */ 
      ) /* -> NO NEED TO MAPPING :[android.view.View?] */ )}
  }

  override fun sthIdAName(
    id: String,
    name: String,
    code1: String,
    code2: String,
    routeResultTransformer: (String) -> Unit
  ) {
    // com.mars.component.detail.api.Apis.getSth
    // - parameters:
    // --- id : kotlin.String
    // --- name : kotlin.String
    // --- code1 : kotlin.String
    // --- code2 : kotlin.String
    // - resultType:
    // --- kotlin.String
    routeResultTransformer(Apis().getSth(
      id /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      name /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      code1 /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      code2 /* -> NO NEED TO MAPPING :[kotlin.String] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.String] */ )
  }

  override fun doSthVararg(no: Int, vararg varargString: String) {
    // com.mars.component.detail.api.Apis.doSthVararg
    // - parameters:
    // --- no : kotlin.Int
    // --- varargString : vararg kotlin.Array<kotlin.String>
    // - resultType:
    // --- null
    Apis().doSthVararg(
      no /* -> NO NEED TO MAPPING :[kotlin.Int] */ ,
      *varargString /* -> NO NEED TO MAPPING :[kotlin.Array<kotlin.String>] */ 
    )
  }

  override fun doSthVarargHof(no: Int, vararg varargString: Function5<Activity, ViewModelStoreOwner,
      LifecycleOwner, LifecycleOwner?, LifecycleOwner?, LifecycleOwner?>) {
    // com.mars.component.detail.api.Apis.doSthVarargHof
    // - parameters:
    // --- no : kotlin.Int
    // --- varargString : vararg kotlin.Array<kotlin.Function5<android.app.Activity, androidx.lifecycle.ViewModelStoreOwner, androidx.lifecycle.LifecycleOwner, androidx.lifecycle.LifecycleOwner?, androidx.lifecycle.LifecycleOwner?, androidx.lifecycle.LifecycleOwner?>>
    // - resultType:
    // --- null
    Apis().doSthVarargHof(
      no /* -> NO NEED TO MAPPING :[kotlin.Int] */ ,
      *varargString /* -> NO NEED TO MAPPING :[kotlin.Array<kotlin.Function5<android.app.Activity, androidx.lifecycle.ViewModelStoreOwner, androidx.lifecycle.LifecycleOwner, androidx.lifecycle.LifecycleOwner?, androidx.lifecycle.LifecycleOwner?, androidx.lifecycle.LifecycleOwner?>>] */ 
    )
  }

  override fun doSthBean(a1: TestDataBeanCtx, routeResultTransformer: (TestListBeanCtx) -> Unit) {
    // com.mars.component.detail.api.Apis.doSthBean
    // - parameters:
    // --- a1 : com.mars.component.detail.value.TestDataBean
    // - resultType:
    // --- com.mars.component.detail.value.TestListBean
    routeResultTransformer(Apis().doSthBean(
      a1.toTypeOfT() /* -> TO ORIGINAL TYPE :[com.mars.component.detail.value.TestDataBean] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestListBean] */ )
  }

  override fun doSthUriCrash(name: String, code: String) {
    // com.mars.component.detail.api.Apis.doSthUriCrash3
    // - parameters:
    // --- name : kotlin.String
    // --- code : kotlin.String
    // - resultType:
    // --- null
    Apis().doSthUriCrash3(
      name /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      code /* -> NO NEED TO MAPPING :[kotlin.String] */ 
    )
  }

  override fun doSthUriCrashByNameCodeVersion(
    name: String,
    code: String,
    version: String
  ) {
    // com.mars.component.detail.api.Apis.doSthUriCrash4
    // - parameters:
    // --- name : kotlin.String
    // --- code : kotlin.String
    // --- version : kotlin.String
    // - resultType:
    // --- null
    Apis().doSthUriCrash4(
      name /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      code /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      version /* -> NO NEED TO MAPPING :[kotlin.String] */ 
    )
  }

  override fun doSthMappingBean(a1: TestDataMappingBeanCtx,
      routeResultTransformer: (TestDataMappingBeanCtx) -> Unit) {
    // com.mars.component.detail.api.Apis.doSthMappingBean
    // - parameters:
    // --- a1 : com.mars.component.detail.value.mapping.TestDataMappingBean
    // - resultType:
    // --- com.mars.component.detail.value.mapping.TestDataMappingBean
    routeResultTransformer(Apis().doSthMappingBean(
      a1.mapToType(toTestDataMappingBeanOrgMapping) /* -> TO ORIGINAL TYPE :[com.mars.component.detail.value.mapping.TestDataMappingBean] */ 
    ).mapToType(toTestDataMappingBeanCtxMapping) /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataMappingBean] */ )
  }

  override fun doSthTestNestDataMappingBean(bean: TestNestDataMappingBeanCtx,
      routeResultTransformer: (TestNestDataMappingBeanCtx) -> Unit) {
    // com.mars.component.detail.api.Apis.doSthTestNestDataMappingBean
    // - parameters:
    // --- bean : com.mars.component.detail.value.mapping.TestNestDataMappingBean
    // - resultType:
    // --- com.mars.component.detail.value.mapping.TestNestDataMappingBean
    routeResultTransformer(Apis().doSthTestNestDataMappingBean(
      bean.mapToType(toTestNestDataMappingBeanOrgMapping) /* -> TO ORIGINAL TYPE :[com.mars.component.detail.value.mapping.TestNestDataMappingBean] */ 
    ).mapToType(toTestNestDataMappingBeanCtxMapping) /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestNestDataMappingBean] */ )
  }

  override fun doSthAsyncHOF(hofCallback: (String, TestDataBeanCtx) -> Unit) {
    // com.mars.component.detail.api.ApisAsyncReturn.doSthAsyncHOF
    // - parameters:
    // --- hof : kotlin.Function2<kotlin.String, com.mars.component.detail.value.TestDataBean, kotlin.Unit>
    // - resultType:
    // --- null
    val hofCallbackCallbackTransformer: (Any?, Any?) -> Unit = { lambdaArg0, lambdaArg1 -> 
      hofCallback(
        lambdaArg0.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
        lambdaArg1.toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean] */ 
      )}
    ApisAsyncReturn().doSthAsyncHOF(
      hofCallbackCallbackTransformer
    )
  }

  override fun doSthAsyncHOFNullable(
    hof2xxCallback: Function2<String, TestDataBeanCtx, Unit>?,
    hof4xxCallback: Function3<String, Int, TestDataBeanCtx, TestDataBeanCtx?>?,
    hofx1resCallback: ((String, TestDataBeanCtx) -> Unit)?
  ) {
    // com.mars.component.detail.api.ApisAsyncReturn.doSthAsyncHOFNullable
    // - parameters:
    // --- hofx1res : kotlin.Function2<kotlin.String, com.mars.component.detail.value.TestDataBean, kotlin.Unit>?
    // --- hof2xx : kotlin.Function2<kotlin.String, com.mars.component.detail.value.TestDataBean, kotlin.Unit>?
    // --- hof4xx : kotlin.Function3<kotlin.String, kotlin.Int, com.mars.component.detail.value.TestDataBean, com.mars.component.detail.value.TestDataBean?>?
    // - resultType:
    // --- null
    val hofx1resCallbackCallbackTransformer: (Any?, Any?) -> Unit = { lambdaArg0, lambdaArg1 -> 
      hofx1resCallback?.invoke(
        lambdaArg0.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
        lambdaArg1.toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean] */ 
      )}
    val hof2xxCallbackCallbackTransformer: (Any?, Any?) -> Unit = { lambdaArg0, lambdaArg1 -> 
      hof2xxCallback?.invoke(
        lambdaArg0.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
        lambdaArg1.toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean] */ 
      )}
    val hof4xxCallbackCallbackTransformer: (Any?, Any?, Any?) -> TestDataBeanOrg? = { lambdaArg0, lambdaArg1, lambdaArg2 -> 
      hof4xxCallback?.invoke(
        lambdaArg0.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
        lambdaArg1.castToTypeOfT() /* -> NO NEED TO MAPPING :[kotlin.Int] */ ,
        lambdaArg2.toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean] */ 
      )?.toTypeOfT() /* -> TO ORIGINAL TYPE :[com.mars.component.detail.value.TestDataBean?] */ }
    ApisAsyncReturn().doSthAsyncHOFNullable(
      hofx1resCallbackCallbackTransformer,
      hof2xxCallbackCallbackTransformer,
      hof4xxCallbackCallbackTransformer
    )
  }

  override fun doSthAsync2HOF(hofCallback: (String, Int) -> Unit, hof2Callback: (String, Int) ->
      Unit) {
    // com.mars.component.detail.api.ApisAsyncReturn.doSthAsyncHOF
    // - parameters:
    // --- hof : kotlin.Function2<kotlin.String, kotlin.Int, kotlin.Unit>
    // --- hof2 : kotlin.Function2<kotlin.String, kotlin.Int, kotlin.Unit>
    // - resultType:
    // --- null
    ApisAsyncReturn().doSthAsyncHOF(
      hofCallback /* -> NO NEED TO MAPPING :[kotlin.Function2<kotlin.String, kotlin.Int, kotlin.Unit>] */ ,
      hof2Callback /* -> NO NEED TO MAPPING :[kotlin.Function2<kotlin.String, kotlin.Int, kotlin.Unit>] */ 
    )
  }

  override fun doSthAsyncOpen(uriParameter: String, onCallCallback: ((String?, Int) -> Unit)?) {
    // com.mars.component.detail.api.ApisAsyncReturn.doSthAsyncOpen
    // - parameters:
    // --- uri : kotlin.String
    // --- results : com.mars.component.detail.api.Callback?
    // - resultType:
    // --- null
    val resultsParameterCallbackTransformer = object : Callback() {
      override fun onCall(v1: String?, v2: Int) {
        onCallCallback?.invoke(
          v1 /* -> NO NEED TO MAPPING :[kotlin.String?] */ ,
          v2 /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
        )}
    }
    ApisAsyncReturn().doSthAsyncOpen(
      uriParameter /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      resultsParameterCallbackTransformer
    )
  }

  override fun doSthAsyncInterface(onCallCallback: (String?, Int) -> Unit) {
    // com.mars.component.detail.api.ApisAsyncReturn.doSthAsyncInterface
    // - parameters:
    // --- onResult : com.mars.component.detail.api.Callbackable
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

  override fun doSthAsync3Interface(
    onCallCallback: ((String?, Int) -> Unit)?,
    onCall1Callback: (String?, Int) -> Unit,
    onCall2Callback: (TestDataBeanCtx?) -> Unit
  ) {
    // com.mars.component.detail.api.ApisAsyncReturn.doSthAsync2Interface
    // - parameters:
    // --- onResult : com.mars.component.detail.api.Callbackable?
    // --- onResult2 : com.mars.component.detail.api.Callbackable
    // --- onResult3 : com.mars.component.detail.api.BeanCallbackable
    // - resultType:
    // --- null
    val onResultCallbackTransformer = object : Callbackable {
      override fun onCall(v1: String?, v2: Int) {
        onCallCallback?.invoke(
          v1 /* -> NO NEED TO MAPPING :[kotlin.String?] */ ,
          v2 /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
        )}
    }
    val onResult2CallbackTransformer = object : Callbackable {
      override fun onCall(v1: String?, v2: Int) {
        onCall1Callback(
          v1 /* -> NO NEED TO MAPPING :[kotlin.String?] */ ,
          v2 /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
        )}
    }
    val onResult3CallbackTransformer = object : BeanCallbackable {
      override fun onCall(v: TestDataBeanOrg?) {
        onCall2Callback(
          v?.toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean?] */ 
        )}
    }
    ApisAsyncReturn().doSthAsync2Interface(
      onResultCallbackTransformer,
      onResult2CallbackTransformer,
      onResult3CallbackTransformer
    )
  }

  override fun doSthAsyncInterfaceMultiFunc(
    startCallback: (String?, Int) -> Unit,
    dataCallback: (TestDataBeanCtx?) -> Unit,
    stopCallback: (String?, Int) -> Unit
  ) {
    // com.mars.component.detail.api.ApisAsyncReturn.doSthAsyncInterfaceMultiFunc
    // - parameters:
    // --- onResult : com.mars.component.detail.api.MultiCallback
    // - resultType:
    // --- null
    val onResultCallbackTransformer = object : MultiCallback {
      override fun start(v1: String?, v2: Int) {
        startCallback(
          v1 /* -> NO NEED TO MAPPING :[kotlin.String?] */ ,
          v2 /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
        )}
      override fun data(v: TestDataBeanOrg?) {
        dataCallback(
          v?.toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean?] */ 
        )}
      override fun stop(v1: String?, v2: Int) {
        stopCallback(
          v1 /* -> NO NEED TO MAPPING :[kotlin.String?] */ ,
          v2 /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
        )}
    }
    ApisAsyncReturn().doSthAsyncInterfaceMultiFunc(
      onResultCallbackTransformer
    )
  }

  override fun doSthHOFTop(lambdaArg0: Unit, routeResultTransformer: (Unit) -> Unit) {
    // com.mars.component.detail.api.AipsInTopKt.doSthHOFTop
    // - parameters:
    // --- lambdaArg0 : kotlin.Unit
    // - resultType:
    // --- kotlin.Unit
    routeResultTransformer(com.mars.component.detail.api.doSthHOFTop(
      lambdaArg0 /* -> NO NEED TO MAPPING :[kotlin.Unit] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.Unit] */ )
  }

  override fun propertyTop(routeResultTransformer: (String) -> Unit) {
    // com.mars.component.detail.api.AipsInTopKt.propertyTop
    // - resultType:
    // --- kotlin.String
    routeResultTransformer(com.mars.component.detail.api.propertyTop /* -> NO NEED TO MAPPING :[kotlin.String] */ )
  }

  override fun doSthTop(
    ints: Array<Int>,
    li: List<Int?>,
    strings: Array<String>,
    ls: List<String>,
    beans: Array<TestDataBeanCtx>,
    lb: List<TestDataBeanCtx>,
    routeResultTransformer: (List<TestDataBeanCtx>) -> Unit
  ) {
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
    routeResultTransformer(com.mars.component.detail.api.doSthTop(
      ints /* -> NO NEED TO MAPPING :[kotlin.Array<kotlin.Int>] */ ,
      li /* -> NO NEED TO MAPPING :[kotlin.collections.List<kotlin.Int?>] */ ,
      strings /* -> NO NEED TO MAPPING :[kotlin.Array<kotlin.String>] */ ,
      ls /* -> NO NEED TO MAPPING :[kotlin.collections.List<kotlin.String>] */ ,
      beans.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.Array<com.mars.component.detail.value.TestDataBean>] */ ,
      lb.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.List<com.mars.component.detail.value.TestDataBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean>] */ )
  }

  override fun getFilesMeta(
    context: Context?,
    pathParameter: String?,
    onResultCallback: (Long, String?) -> Unit
  ) {
    // com.mars.component.detail.api.ApisBadCase.getFilesMeta
    // - parameters:
    // --- context : android.content.Context?
    // --- path : kotlin.String?
    // --- callback : com.mars.component.detail.api.GetFileMetaCallback
    // - resultType:
    // --- null
    val callbackCallbackTransformer = object : GetFileMetaCallback {
      override fun onResult(fsid: Long, pathParameter: String?) {
        onResultCallback(
          fsid /* -> NO NEED TO MAPPING :[kotlin.Long] */ ,
          pathParameter /* -> NO NEED TO MAPPING :[kotlin.String?] */ 
        )}
    }
    ApisBadCase().getFilesMeta(
      context /* -> NO NEED TO MAPPING :[android.content.Context?] */ ,
      pathParameter /* -> NO NEED TO MAPPING :[kotlin.String?] */ ,
      callbackCallbackTransformer
    )
  }

  override fun enterpriseBusinessAllocateTicket(
    context: Context,
    shareId: Long,
    count: Int,
    packetType: Int,
    routeResultTransformer: ((LiveData<List<String>>?) -> Unit)?
  ) {
    // com.mars.component.detail.api.ApisBadCase.allocateTicket
    // - parameters:
    // --- context : android.content.Context
    // --- shareId : kotlin.Long
    // --- count : kotlin.Int
    // --- packetType : kotlin.Int
    // - resultType:
    // --- androidx.lifecycle.LiveData<kotlin.collections.List<kotlin.String>>?
    if (null != routeResultTransformer) {
      routeResultTransformer(ApisBadCase().allocateTicket(
        context /* -> NO NEED TO MAPPING :[android.content.Context] */ ,
        shareId /* -> NO NEED TO MAPPING :[kotlin.Long] */ ,
        count /* -> NO NEED TO MAPPING :[kotlin.Int] */ ,
        packetType /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
      ) /* -> NO NEED TO MAPPING :[androidx.lifecycle.LiveData<kotlin.collections.List<kotlin.String>>?] */ )}
  }

  override fun doSthProvideInstanceByFunc() {
    // com.mars.component.detail.api.Task.doSthProvideObject
    // - resultType:
    // --- null
    com.mars.component.detail.api.provideTask() /* -> NO NEED TO MAPPING :[com.mars.component.detail.api.Task] */ .doSthProvideObject()
  }

  override fun doSthProvideInstanceByFunc2() {
    // com.mars.component.detail.api.Task.doSthProvideObject2
    // - resultType:
    // --- null
    com.mars.component.detail.api.provideTask() /* -> NO NEED TO MAPPING :[com.mars.component.detail.api.Task] */ .doSthProvideObject2()
  }

  override fun doSthProvideInstanceByParameterFunc(
    rubikInstanceValue: String,
    rubikInstanceV0: Int?,
    v1: String,
    v2: Int?,
    v3: Int?
  ) {
    // com.mars.component.detail.api.Task.doSthProvideObject2
    // - parameters:
    // --- v1 : kotlin.String
    // --- v2 : kotlin.Int?
    // --- v3 : kotlin.Int?
    // - resultType:
    // --- null
    com.mars.component.detail.api.provideTask2(
      rubikInstanceValue /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      rubikInstanceV0 /* -> NO NEED TO MAPPING :[kotlin.Int?] */ 
    ) /* -> NO NEED TO MAPPING :[com.mars.component.detail.api.Task] */ .doSthProvideObject2(
      v1 /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      v2 /* -> NO NEED TO MAPPING :[kotlin.Int?] */ ,
      v3 /* -> NO NEED TO MAPPING :[kotlin.Int?] */ 
    )
  }

  override fun doSthProvideInstanceByConstructor(
    rubikInstanceValue: String,
    v1: String,
    v2: Int?,
    v3: Int?
  ) {
    // com.mars.component.detail.api.Task.doSthProvideObject3
    // - parameters:
    // --- v1 : kotlin.String
    // --- v2 : kotlin.Int?
    // --- v3 : kotlin.Int?
    // - resultType:
    // --- null
    Task(
      rubikInstanceValue /* -> NO NEED TO MAPPING :[kotlin.String] */ 
    ) /* -> NO NEED TO MAPPING :[com.mars.component.detail.api.Task] */ .doSthProvideObject3(
      v1 /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      v2 /* -> NO NEED TO MAPPING :[kotlin.Int?] */ ,
      v3 /* -> NO NEED TO MAPPING :[kotlin.Int?] */ 
    )
  }

  override fun doSthResultReceiver(resultParameter: ResultReceiver,
      routeResultTransformer: (ResultReceiver) -> Unit) {
    // com.mars.component.detail.api.ApisWithResultReceiver.doSthResultReceiver
    // - parameters:
    // --- result : android.os.ResultReceiver
    // - resultType:
    // --- android.os.ResultReceiver
    routeResultTransformer(ApisWithResultReceiver().doSthResultReceiver(
      resultParameter /* -> NO NEED TO MAPPING :[android.os.ResultReceiver] */ 
    ) /* -> NO NEED TO MAPPING :[android.os.ResultReceiver] */ )
  }

  override fun liveDataGet(routeResultTransformer: (LiveData<String>) -> Unit) {
    // com.mars.component.detail.api.ApisWithLiveData.getSthLiveData
    // - resultType:
    // --- androidx.lifecycle.LiveData<kotlin.String>
    routeResultTransformer(ApisWithLiveData().getSthLiveData() /* -> NO NEED TO MAPPING :[androidx.lifecycle.LiveData<kotlin.String>] */ )
  }

  override fun liveDataBeanGet(routeResultTransformer: (LiveData<TestDataBeanCtx>) -> Unit) {
    // com.mars.component.detail.api.ApisWithLiveData.getSthLiveDataBean
    // - resultType:
    // --- androidx.lifecycle.LiveData<com.mars.component.detail.value.TestDataBean>
    routeResultTransformer(ApisWithLiveData().getSthLiveDataBean().toTypeOfT() /* -> TO CONTEXT TYPE :[androidx.lifecycle.LiveData<rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean>] */ )
  }

  override fun liveDataBeanListGet(routeResultTransformer: (LiveData<List<TestListBeanCtx?>>) ->
      Unit) {
    // com.mars.component.detail.api.ApisWithLiveData.getSthLiveDataBeanList
    // - resultType:
    // --- androidx.lifecycle.LiveData<kotlin.collections.List<com.mars.component.detail.value.TestListBean?>>
    routeResultTransformer(ApisWithLiveData().getSthLiveDataBeanList().toTypeOfT() /* -> TO CONTEXT TYPE :[androidx.lifecycle.LiveData<kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.TestListBean?>>] */ )
  }

  override fun doSthCallbackObject(callback: CallbackTestInterfaceCtx) {
    // com.mars.component.detail.callback.CallbackTestInterfaceKt.doSthCallbackObject
    // - parameters:
    // --- callback : com.mars.component.detail.callback.CallbackTestInterface
    // - resultType:
    // --- null
    com.mars.component.detail.callback.doSthCallbackObject(
      callback.mapToType(toCallbackTestInterfaceOrgMapping) /* -> TO ORIGINAL TYPE :[com.mars.component.detail.callback.CallbackTestInterface] */ 
    )
  }

  override fun testNameString(): String? {
    // com.mars.component.detail.test.TestDefaultPathTask.testNameString
    // - resultType:
    // --- kotlin.String
    return TestDefaultPathTask().testNameString /* -> NO NEED TO MAPPING :[kotlin.String] */ 
  }

  override fun testName() {
    // com.mars.component.detail.test.TestDefaultPathTask.testName
    // - resultType:
    // --- null
    TestDefaultPathTask().testName()
  }

  override fun testNameUp() {
    // com.mars.component.detail.test.TestDefaultPathTask.TEST_NAME_UP
    // - resultType:
    // --- null
    TestDefaultPathTask().TEST_NAME_UP()
  }

  override fun getALotOfCommonInstance(i: Int): List<ObjectTestCtx>? {
    // com.mars.component.detail.objekt.ObjectTestKt.getALotOfObjectTest
    // - parameters:
    // --- i : kotlin.Int
    // - resultType:
    // --- kotlin.collections.List<com.mars.component.detail.objekt.ObjectTest>
    return com.mars.component.detail.objekt.getALotOfObjectTest(
      i /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
    ).mapToType(toObjectTestCtxMapping) /* -> TO CONTEXT TYPE :[kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.objekt.ObjectTest>] */ 
  }

  override fun sendBackALotOfCommonInstance(objs: Map<String, ObjectTestCtx>) {
    // com.mars.component.detail.objekt.ObjectTestKt.senBackALotOfObjectTest
    // - parameters:
    // --- objs : kotlin.collections.Map<kotlin.String, com.mars.component.detail.objekt.ObjectTest>
    // - resultType:
    // --- null
    com.mars.component.detail.objekt.senBackALotOfObjectTest(
      objs.mapToType(toObjectTestOrgMapping) /* -> TO ORIGINAL TYPE :[kotlin.collections.Map<kotlin.String, com.mars.component.detail.objekt.ObjectTest>] */ 
    )
  }

  override fun getALotOfCommonInstanceNull(i: Int): List<ObjectTestCtx?>? {
    // com.mars.component.detail.objekt.ObjectTestKt.getALotOfObjectTestNull
    // - parameters:
    // --- i : kotlin.Int
    // - resultType:
    // --- kotlin.collections.List<com.mars.component.detail.objekt.ObjectTest?>?
    return com.mars.component.detail.objekt.getALotOfObjectTestNull(
      i /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
    )?.mapToType(toNullableObjectTestCtxMapping) /* -> TO CONTEXT TYPE :[kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.objekt.ObjectTest?>?] */ 
  }

  override fun sendBackALotOfCommonInstanceNull(objs: Map<String?, ObjectTestCtx?>?) {
    // com.mars.component.detail.objekt.ObjectTestKt.senBackALotOfObjectTesNull
    // - parameters:
    // --- objs : kotlin.collections.Map<kotlin.String?, com.mars.component.detail.objekt.ObjectTest?>?
    // - resultType:
    // --- null
    com.mars.component.detail.objekt.senBackALotOfObjectTesNull(
      objs?.mapToType(toNullableObjectTestOrgMapping) /* -> TO ORIGINAL TYPE :[kotlin.collections.Map<kotlin.String?, com.mars.component.detail.objekt.ObjectTest?>?] */ 
    )
  }

  override fun fragmentPage1(): Fragment? {
    // com.mars.component.detail.ui.FirstPageFragment
    // - resultType:
    // --- com.mars.component.detail.ui.FirstPageFragment
    return FirstPageFragment() /* -> NO NEED TO MAPPING :[com.mars.component.detail.ui.FirstPageFragment] */ 
  }

  override fun objectGetDoSthInCommonIns(
    objectInstance: ObjectTestCtx,
    v1: String,
    v2: Int?,
    v3: Int?
  ) {
    // com.mars.component.detail.objekt.ObjectTest.doSthInObject3
    // - parameters:
    // --- v1 : kotlin.String
    // --- v2 : kotlin.Int?
    // --- v3 : kotlin.Int?
    // - resultType:
    // --- null
    objectInstance.mapToType(toObjectTestOrgMapping) /* -> TO ORIGINAL TYPE :[com.mars.component.detail.objekt.ObjectTest] */ .doSthInObject3(
      v1 /* -> NO NEED TO MAPPING :[kotlin.String] */ ,
      v2 /* -> NO NEED TO MAPPING :[kotlin.Int?] */ ,
      v3 /* -> NO NEED TO MAPPING :[kotlin.Int?] */ 
    )
  }

  override fun apiBigdataJsonArray(array: Array<TestDataBeanCtx>): Array<TestDataBeanCtx>? {
    // com.mars.component.detail.api.ApisBigData.getBigJsonArray
    // - parameters:
    // --- array : kotlin.Array<com.mars.component.detail.value.TestDataBean>
    // - resultType:
    // --- kotlin.Array<com.mars.component.detail.value.TestDataBean>
    return ApisBigData().getBigJsonArray(
      array.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.Array<com.mars.component.detail.value.TestDataBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.Array<rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean>] */ 
  }

  override fun apiBigdataParcelArray(array: Array<TestParcelizeBeanCtx>):
      Array<TestParcelizeBeanCtx>? {
    // com.mars.component.detail.api.ApisBigData.getBigParcelArray
    // - parameters:
    // --- array : kotlin.Array<com.mars.component.detail.value.TestParcelizeBean>
    // - resultType:
    // --- kotlin.Array<com.mars.component.detail.value.TestParcelizeBean>
    return ApisBigData().getBigParcelArray(
      array.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.Array<com.mars.component.detail.value.TestParcelizeBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.Array<rubik.generate.context.demo_com_mars_rubik_test_detail.TestParcelizeBean>] */ 
  }

  override fun apiBigdataLibArray(array: Array<TestDataBeanCtx>): Array<TestDataBeanCtx>? {
    // com.mars.component.detail.api.ApisBigData.getBigLibArray
    // - parameters:
    // --- array : kotlin.Array<com.mars.component.detail.value.TestDataBean>
    // - resultType:
    // --- kotlin.Array<com.mars.component.detail.value.TestDataBean>
    return ApisBigData().getBigLibArray(
      array.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.Array<com.mars.component.detail.value.TestDataBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.Array<rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean>] */ 
  }

  override fun apiBigdataJsonList(list: List<TestDataBeanCtx>): List<TestDataBeanCtx>? {
    // com.mars.component.detail.api.ApisBigData.getBigJsonList
    // - parameters:
    // --- list : kotlin.collections.List<com.mars.component.detail.value.TestDataBean>
    // - resultType:
    // --- kotlin.collections.List<com.mars.component.detail.value.TestDataBean>
    return ApisBigData().getBigJsonList(
      list.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.List<com.mars.component.detail.value.TestDataBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean>] */ 
  }

  override fun apiBigdataParcelList(list: List<TestParcelizeBeanCtx>): List<TestParcelizeBeanCtx>? {
    // com.mars.component.detail.api.ApisBigData.getBigParcelList
    // - parameters:
    // --- list : kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>
    // - resultType:
    // --- kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>
    return ApisBigData().getBigParcelList(
      list.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.TestParcelizeBean>] */ 
  }

  override fun apiBigdataLibList(list: List<TestDataBeanCtx>): List<TestDataBeanCtx>? {
    // com.mars.component.detail.api.ApisBigData.getBigLibList
    // - parameters:
    // --- list : kotlin.collections.List<com.mars.component.detail.value.TestDataBean>
    // - resultType:
    // --- kotlin.collections.List<com.mars.component.detail.value.TestDataBean>
    return ApisBigData().getBigLibList(
      list.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.List<com.mars.component.detail.value.TestDataBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean>] */ 
  }

  override fun apiBigdataParcelArraylist(list: List<TestParcelizeBeanCtx>):
      List<TestParcelizeBeanCtx>? {
    // com.mars.component.detail.api.ApisBigData.getBigParcelArrayList
    // - parameters:
    // --- list : kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>
    // - resultType:
    // --- kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>
    return ApisBigData().getBigParcelArrayList(
      list.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.TestParcelizeBean>] */ 
  }

  override fun apiSerializationParcelBean(parcelBean: TestParcelizeBeanCtx?):
      TestParcelizeBeanCtx? {
    // com.mars.component.detail.api.ApiSerialization.doSthParcelBean
    // - parameters:
    // --- parcelBean : com.mars.component.detail.value.TestParcelizeBean?
    // - resultType:
    // --- com.mars.component.detail.value.TestParcelizeBean
    return ApiSerialization().doSthParcelBean(
      parcelBean?.toTypeOfT() /* -> TO ORIGINAL TYPE :[com.mars.component.detail.value.TestParcelizeBean?] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestParcelizeBean] */ 
  }

  override fun apiSerializationParcelArray(parcels: Array<TestParcelizeBeanCtx>):
      Array<TestParcelizeBeanCtx>? {
    // com.mars.component.detail.api.ApiSerialization.doSthParcelArray
    // - parameters:
    // --- parcels : kotlin.Array<com.mars.component.detail.value.TestParcelizeBean>
    // - resultType:
    // --- kotlin.Array<com.mars.component.detail.value.TestParcelizeBean>
    return ApiSerialization().doSthParcelArray(
      parcels.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.Array<com.mars.component.detail.value.TestParcelizeBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.Array<rubik.generate.context.demo_com_mars_rubik_test_detail.TestParcelizeBean>] */ 
  }

  override fun apiSerializationParcelList(parcels: List<TestParcelizeBeanCtx>):
      List<TestParcelizeBeanCtx>? {
    // com.mars.component.detail.api.ApiSerialization.doSthParcelList
    // - parameters:
    // --- parcels : kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>
    // - resultType:
    // --- kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>
    return ApiSerialization().doSthParcelList(
      parcels.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.List<com.mars.component.detail.value.TestParcelizeBean>] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.TestParcelizeBean>] */ 
  }

  override fun apiSerializationSerializableBean(serializableBean: TestSerializableBeanCtx):
      TestSerializableBeanCtx? {
    // com.mars.component.detail.api.ApiSerialization.doSthSerializableBean
    // - parameters:
    // --- serializableBean : com.mars.component.detail.value.TestSerializableBean
    // - resultType:
    // --- com.mars.component.detail.value.TestSerializableBean
    return ApiSerialization().doSthSerializableBean(
      serializableBean.toTypeOfT() /* -> TO ORIGINAL TYPE :[com.mars.component.detail.value.TestSerializableBean] */ 
    ).toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestSerializableBean] */ 
  }

  override fun sthNavigationOnlyUri(uriParameter: String, routeResultTransformer: (String) ->
      Unit) {
    // com.mars.component.detail.api.Apis.getSthNavigationOnly
    // - parameters:
    // --- uri : kotlin.String
    // - resultType:
    // --- kotlin.String
    routeResultTransformer(Apis().getSthNavigationOnly(
      uriParameter /* -> NO NEED TO MAPPING :[kotlin.String] */ 
    ) /* -> NO NEED TO MAPPING :[kotlin.String] */ )
  }

  override fun createObjectTestInstance(value: String): ObjectTestCtx? {
    // com.mars.component.detail.objekt.ObjectTest
    // - parameters:
    // --- value : kotlin.String
    // - resultType:
    // --- com.mars.component.detail.objekt.ObjectTest
    return ObjectTestOrg(
      value /* -> NO NEED TO MAPPING :[kotlin.String] */ 
    ).mapToType(toObjectTestCtxMapping) /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.objekt.ObjectTest] */ 
  }

  override fun createObjectTestCreateInstance(v1: Int, v2: String): ObjectTestCreateCtx? {
    // com.mars.component.detail.objekt.ObjectTestCreate
    // - parameters:
    // --- v1 : kotlin.Int
    // --- v2 : kotlin.String
    // - resultType:
    // --- com.mars.component.detail.objekt.ObjectTestCreate
    return ObjectTestCreateOrg(
      v1 /* -> NO NEED TO MAPPING :[kotlin.Int] */ ,
      v2 /* -> NO NEED TO MAPPING :[kotlin.String] */ 
    ).mapToType(toObjectTestCreateCtxMapping) /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.objekt.ObjectTestCreate] */ 
  }

  override fun createObjectTestCreateInstanceByV1V2V3V4(
    v1: Int,
    v2: Int,
    v3: Int,
    v4: Int
  ): ObjectTestCreateCtx? {
    // com.mars.component.detail.objekt.ObjectTestCreate
    // - parameters:
    // --- v1 : kotlin.Int
    // --- v2 : kotlin.Int
    // --- v3 : kotlin.Int
    // --- v4 : kotlin.Int
    // - resultType:
    // --- com.mars.component.detail.objekt.ObjectTestCreate
    return ObjectTestCreateOrg(
      v1 /* -> NO NEED TO MAPPING :[kotlin.Int] */ ,
      v2 /* -> NO NEED TO MAPPING :[kotlin.Int] */ ,
      v3 /* -> NO NEED TO MAPPING :[kotlin.Int] */ ,
      v4 /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
    ).mapToType(toObjectTestCreateCtxMapping) /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.objekt.ObjectTestCreate] */ 
  }

  @RGenerated(
    kind = "aggregate_mappings",
    by = "rubik-kapt:1.10.0.0-K1_5-LOCAL",
    version = "0.0.1-DEV"
  )
  @Keep
  object Mappings {
    /**
     *  Mapping between :
     *  com.mars.component.detail.objekt.ObjectTest < --- > rubik.generate.context.demo_com_mars_rubik_test_detail.objekt.ObjectTest 
     */
    val toObjectTestCtxMapping: ObjectTestOrg.() -> ObjectTestCtx
      get() = { ObjectTestCtx.create(this) }

    val toObjectTestOrgMapping: ObjectTestCtx.() -> ObjectTestOrg
      get() = { this.originalObject.castToTypeOfT() }

    val toNullableObjectTestCtxMapping: ObjectTestOrg?.() -> ObjectTestCtx?
      get() = { this?.toObjectTestCtxMapping() }

    val toNullableObjectTestOrgMapping: ObjectTestCtx?.() -> ObjectTestOrg?
      get() = { this?.toObjectTestOrgMapping() }

    /**
     *  Mapping between :
     *  com.mars.component.detail.objekt.ObjectTestCreate < --- > rubik.generate.context.demo_com_mars_rubik_test_detail.objekt.ObjectTestCreate 
     */
    val toObjectTestCreateCtxMapping: ObjectTestCreateOrg.() -> ObjectTestCreateCtx
      get() = { ObjectTestCreateCtx.create(this) }

    val toObjectTestCreateOrgMapping: ObjectTestCreateCtx.() -> ObjectTestCreateOrg
      get() = { this.originalObject.castToTypeOfT() }

    /**
     *  Mapping between :
     *  com.mars.component.detail.callback.CallbackTestInterface < --- > rubik.generate.context.demo_com_mars_rubik_test_detail.callback.CallbackTestInterface 
     */
    val toCallbackTestInterfaceOrgMapping: CallbackTestInterfaceCtx.() -> CallbackTestInterfaceOrg
      get() =  {
        val mappingTarget = this
        object : CallbackTestInterfaceOrg {
          override fun callbackInt(i: Int) {
            mappingTarget.callbackInt(
              i /* -> NO NEED TO MAPPING :[kotlin.Int] */ 
            )}
          override fun callbackBean(bean: TestDataBeanOrg) {
            mappingTarget.callbackBean(
              bean.toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean] */ 
            )}
          override fun callbackBeanReBean(bean: TestDataBeanOrg?) {
            mappingTarget.callbackBeanReBean(
              bean?.toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean?] */ 
            )}
        }
      }

    /**
     *  Mapping between :
     *  com.mars.component.detail.value.mapping.TestNestDataMappingBean < --- > rubik.generate.context.demo_com_mars_rubik_test_detail.TestNestDataMappingBean 
     */
    val toTestNestDataMappingBeanCtxMapping: TestNestDataMappingBeanOrg.() ->
        TestNestDataMappingBeanCtx
      get() = { TestNestDataMappingBeanCtx(
        d1?.toTypeOfT() /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean?] */ ,
        d2?.mapToType(toNullableTestDataMappingBeanCtxMapping) /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataMappingBean?] */ ,
        d3?.mapToType(toNullableTestNestDataMappingBeanCtxMapping) /* -> TO CONTEXT TYPE :[rubik.generate.context.demo_com_mars_rubik_test_detail.TestNestDataMappingBean?] */ ,
        d4?.toTypeOfT() /* -> TO CONTEXT TYPE :[kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataBean>?] */ ,
        d5?.mapToType(toTestDataMappingBeanCtxMapping) /* -> TO CONTEXT TYPE :[kotlin.collections.List<rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataMappingBean>?] */ 
      ) }

    val toTestNestDataMappingBeanOrgMapping: TestNestDataMappingBeanCtx.() ->
        TestNestDataMappingBeanOrg
      get() = { TestNestDataMappingBeanOrg(
        d1?.toTypeOfT() /* -> TO ORIGINAL TYPE :[com.mars.component.detail.value.TestDataBean?] */ ,
        d2?.mapToType(toNullableTestDataMappingBeanOrgMapping) /* -> TO ORIGINAL TYPE :[com.mars.component.detail.value.mapping.TestDataMappingBean?] */ ,
        d3?.mapToType(toNullableTestNestDataMappingBeanOrgMapping) /* -> TO ORIGINAL TYPE :[com.mars.component.detail.value.mapping.TestNestDataMappingBean?] */ ,
        d4?.toTypeOfT() /* -> TO ORIGINAL TYPE :[kotlin.collections.List<com.mars.component.detail.value.TestDataBean>?] */ ,
        d5?.mapToType(toTestDataMappingBeanOrgMapping) /* -> TO ORIGINAL TYPE :[kotlin.collections.List<com.mars.component.detail.value.mapping.TestDataMappingBean>?] */ 
      ) }

    val toNullableTestNestDataMappingBeanCtxMapping: TestNestDataMappingBeanOrg?.() ->
        TestNestDataMappingBeanCtx?
      get() = { this?.toTestNestDataMappingBeanCtxMapping() }

    val toNullableTestNestDataMappingBeanOrgMapping: TestNestDataMappingBeanCtx?.() ->
        TestNestDataMappingBeanOrg?
      get() = { this?.toTestNestDataMappingBeanOrgMapping() }

    /**
     *  Mapping between :
     *  com.mars.component.detail.value.mapping.TestDataMappingBean < --- > rubik.generate.context.demo_com_mars_rubik_test_detail.TestDataMappingBean 
     */
    val toTestDataMappingBeanCtxMapping: TestDataMappingBeanOrg.() -> TestDataMappingBeanCtx
      get() = { TestDataMappingBeanCtx(
        d1 /* -> NO NEED TO MAPPING :[kotlin.Int?] */ ,
        d2 /* -> NO NEED TO MAPPING :[kotlin.String?] */ 
      ) }

    val toTestDataMappingBeanOrgMapping: TestDataMappingBeanCtx.() -> TestDataMappingBeanOrg
      get() = { TestDataMappingBeanOrg(
        d1 /* -> NO NEED TO MAPPING :[kotlin.Int?] */ ,
        d2 /* -> NO NEED TO MAPPING :[kotlin.String?] */ 
      ) }

    val toNullableTestDataMappingBeanCtxMapping: TestDataMappingBeanOrg?.() ->
        TestDataMappingBeanCtx?
      get() = { this?.toTestDataMappingBeanCtxMapping() }

    val toNullableTestDataMappingBeanOrgMapping: TestDataMappingBeanCtx?.() ->
        TestDataMappingBeanOrg?
      get() = { this?.toTestDataMappingBeanOrgMapping() }
  }

  @RGenerated(
    kind = "aggregate_companion",
    by = "rubik-kapt:1.10.0.0-K1_5-LOCAL",
    version = "0.0.1-DEV"
  )
  @Keep
  companion object : AggregateFactory() {
    override val URI: String = "demo://com.mars.rubik-test.detail"

    override val EVENT_MSGS: List<String> = listOf(
        "MY_INIT",
        "MY_DESTROY",
        "MY_CALLBACK_RES",
        "MY_CALLBACK_RES_BEAN",
        "MY_CALLBACK_BEAN",
        "LifeCycleEvent_Init",
        "LifeCycleEvent_Destroy"
        )

    override val CREATOR: Function0<Aggregatable> = {DetailAggregate()}
  }
}

/**
 * generated Rubik AggregateId.
 *
 * uri: [demo://com.mars.rubik-test.detail] 
 * originalToken: 
 * [API]api/bigdata/json-array|N|F|T|T|[IVO]getBigJsonArray|F|N|[QUY]array|Array<TestDataBean>|F|F|F[<]|[RST]B|Array<TestDataBean>[<]|ApisBigData|F[<][<]
 * [API]api/bigdata/json-list|N|F|T|T|[IVO]getBigJsonList|F|N|[QUY]list|List<TestDataBean>|F|F|F[<]|[RST]B|List<TestDataBean>[<]|ApisBigData|F[<][<]
 * [API]api/bigdata/lib-array|N|F|T|T|[IVO]getBigLibArray|F|N|[QUY]array|Array<TestDataBean>|F|F|F[<]|[RST]B|Array<TestDataBean>[<]|ApisBigData|F[<][<]
 * [API]api/bigdata/lib-list|N|F|T|T|[IVO]getBigLibList|F|N|[QUY]list|List<TestDataBean>|F|F|F[<]|[RST]B|List<TestDataBean>[<]|ApisBigData|F[<][<]
 * [API]api/bigdata/parcel-array|N|F|T|T|[IVO]getBigParcelArray|F|N|[QUY]array|Array<TestParcelizeBean>|F|F|F[<]|[RST]B|Array<TestParcelizeBean>[<]|ApisBigData|F[<][<]
 * [API]api/bigdata/parcel-arraylist|N|F|T|T|[IVO]getBigParcelArrayList|F|N|[QUY]list|List<TestParcelizeBean>|F|F|F[<]|[RST]B|List<TestParcelizeBean>[<]|ApisBigData|F[<][<]
 * [API]api/bigdata/parcel-list|N|F|T|T|[IVO]getBigParcelList|F|N|[QUY]list|List<TestParcelizeBean>|F|F|F[<]|[RST]B|List<TestParcelizeBean>[<]|ApisBigData|F[<][<]
 * [API]api/serialization/Serializable-bean|N|F|T|T|[IVO]doSthSerializableBean|F|N|[QUY]serializableBean|com.mars.component.detail.value.TestSerializableBean|F|F|F[<]|[RST]B|com.mars.component.detail.value.TestSerializableBean[<]|com.mars.component.detail.api.ApiSerialization|F[<][<]
 * [API]api/serialization/parcel-array|N|F|T|T|[IVO]doSthParcelArray|F|N|[QUY]parcels|Array<TestParcelizeBean>|F|F|F[<]|[RST]B|Array<TestParcelizeBean>[<]|com.mars.component.detail.api.ApiSerialization|F[<][<]
 * [API]api/serialization/parcel-bean|N|F|T|T|[IVO]doSthParcelBean|F|N|[QUY]parcelBean|TestParcelizeBean?|F|F|F[<]|[RST]B|TestParcelizeBean[<]|com.mars.component.detail.api.ApiSerialization|F[<][<]
 * [API]api/serialization/parcel-list|N|F|T|T|[IVO]doSthParcelList|F|N|[QUY]parcels|List<TestParcelizeBean>|F|F|F[<]|[RST]B|List<TestParcelizeBean>[<]|com.mars.component.detail.api.ApiSerialization|F[<][<]
 * [API]create-object-test-create-instance|N|T|T|T|[IVO]ObjectTestCreate|F|N|[QUY]v1|Int|F|F|F[<]|[QUY]v2|String|F|F|F[<]|[RST]B|ObjectTestCreate[<]|ObjectTestCreate|F[<][<]
 * [API]create-object-test-create-instance-by-v1-v2-v3-v4|N|T|T|T|[IVO]ObjectTestCreate|F|N|[QUY]v1|Int|F|F|F[<]|[QUY]v2|Int|F|F|F[<]|[QUY]v3|Int|F|F|F[<]|[QUY]v4|Int|F|F|F[<]|[RST]B|ObjectTestCreate[<]|ObjectTestCreate|F[<][<]
 * [API]create-obj2|N|T|T|T|[IVO]ObjectTest|F|N|[QUY]value|String|F|F|F[<]|[RST]B|ObjectTest[<]|ObjectTest|F[<][<]
 * [API]create-test-default-path-task-instance|java.lang.Object|F|F|F|[IVO]TestDefaultPathTask|F|N|E|[RST]B|TestDefaultPathTask[<]|TestDefaultPathTask|F[<][<]
 * [API]do-sth|N|F|F|F|[IVO]doSth|F|N|E|N|Apis|F[<][<]
 * [API]do-sth-async-3-interface|N|F|F|F|[IVO]doSthAsync2Interface|F|N|[QUY]onResult|com.mars.component.detail.api.Callbackable?|F|F|T[<]|[QUY]onResult2|com.mars.component.detail.api.Callbackable|F|F|T[<]|[QUY]onResult3|com.mars.component.detail.api.BeanCallbackable|F|F|T[<]|N|ApisAsyncReturn|F[<][<]
 * [API]do-sth-async-interface|N|F|F|F|[IVO]doSthAsyncInterface|F|N|[QUY]onResult|com.mars.component.detail.api.Callbackable|F|F|T[<]|N|ApisAsyncReturn|F[<][<]
 * [API]do-sth-async-interface-multi-func|N|F|F|F|[IVO]doSthAsyncInterfaceMultiFunc|F|N|[QUY]onResult|com.mars.component.detail.api.MultiCallback|F|F|T[<]|N|ApisAsyncReturn|F[<][<]
 * [API]do-sth-callback-object|N|F|F|F|[IVO]doSthCallbackObject|T|N|[QUY]callback|com.mars.component.detail.callback.CallbackTestInterface|F|F|F[<]|N|com.mars.component.detail.callback.CallbackTestInterfaceKt|F[<][<]
 * [API]do-sth-create-common-instance|N|F|F|F|[IVO]doSthInCreateObject|F|[OBJ]ObjectTestCreate|create-object-test-create-instance|create-object-test-create-instance-by-v1-v2-v3-v4|do-sth-create-common-instance[<]|[QUY]objectInstance|ObjectTestCreate|F|F|F[<]|[RST]B|TestDataBean[<]|ObjectTestCreate|F[<][<]
 * [API]do-sth-ext|N|F|F|F|[IVO]doSthExt|T|N|[QUY]s|String|F|F|F[<]|[QUY]i|Int|T|F|F[<]|[RST]B|Int[<]|com.mars.component.detail.api.ApisExtKt|F[<][<]
 * [API]2.0/do-sth-hof|N|F|T|F|[IVO]doSthHOF|F|N|[QUY]lambdaArg0|Int|F|F|F[<]|[QUY]lambdaArg1|String|F|F|F[<]|[QUY]lambdaArg2|TestDataBean|F|F|F[<]|[RST]B|Int[<]|Apis|F[<][<]
 * [API]do-sth-in-|N|F|F|F|[IVO]doSthInObject|F|[OBJ]ObjectTest|create-obj2|do-sth-in-|do-sth-in-1|object/get[<]|[QUY]objectInstance|ObjectTest|F|F|F[<]|[RST]B|TestDataBean[<]|ObjectTest|F[<][<]
 * [API]do-sth-in-1|N|F|F|F|[IVO]doSthInObject2|F|[OBJ]ObjectTest|create-obj2|do-sth-in-|do-sth-in-1|object/get[<]|[QUY]objectInstance|ObjectTest|F|F|F[<]|[QUY]v1|String|F|F|F[<]|[QUY]v2|Int?|F|F|F[<]|[QUY]v3|Int?|F|F|F[<]|N|ObjectTest|F[<][<]
 * [API]do-sth-provide-instance-by-constructor|N|F|F|F|[IVO]doSthProvideObject3|F|[AIN]do-sth-provide-instance-by-constructor|[IVO]Task|F|N|[QUY]rubikInstanceValue|String|F|F|F[<]|[RST]B|Task[<]|Task|F[<][<]|[QUY]rubikInstanceValue|String|F|F|F[<]|[QUY]v1|String|F|F|F[<]|[QUY]v2|Int?|F|F|F[<]|[QUY]v3|Int?|F|F|F[<]|N|Task|F[<][<]
 * [API]do-sth-provide-instance-by-func|N|F|F|F|[IVO]doSthProvideObject|F|[AIN]do-sth-provide-instance-by-func|[IVO]provideTask|T|N|E|[RST]B|Task[<]|com.mars.component.detail.api.ApisProvideInstanceKt|F[<][<]|E|N|Task|F[<][<]
 * [API]do-sth-provide-instance-by-func2|N|F|F|F|[IVO]doSthProvideObject2|F|[AIN]do-sth-provide-instance-by-func2|[IVO]provideTask|T|N|E|[RST]B|Task[<]|com.mars.component.detail.api.ApisProvideInstanceKt|F[<][<]|E|N|Task|F[<][<]
 * [API]do-sth-provide-instance-by-parameter-func|N|F|F|F|[IVO]doSthProvideObject2|F|[AIN]do-sth-provide-instance-by-parameter-func|[IVO]provideTask2|T|N|[QUY]rubikInstanceValue|String|F|F|F[<]|[QUY]rubikInstanceV0|Int?|F|F|F[<]|[RST]B|Task[<]|com.mars.component.detail.api.ApisProvideInstanceKt|F[<][<]|[QUY]rubikInstanceValue|String|F|F|F[<]|[QUY]rubikInstanceV0|Int?|F|F|F[<]|[QUY]v1|String|F|F|F[<]|[QUY]v2|Int?|F|F|F[<]|[QUY]v3|Int?|F|F|F[<]|N|Task|F[<][<]
 * [API]do-sth-uri-crash|N|F|F|F|[IVO]doSthUriCrash3|F|N|[QUY]name|String|F|F|F[<]|[QUY]code|String|F|F|F[<]|N|Apis|F[<][<]
 * [API]do-sth-uri-crash-by-name-code-version|N|F|F|F|[IVO]doSthUriCrash4|F|N|[QUY]name|String|F|F|F[<]|[QUY]code|String|F|F|F[<]|[QUY]version|String|F|F|F[<]|N|Apis|F[<][<]
 * [API]doSthAsync2HOF|N|F|F|F|[IVO]doSthAsyncHOF|F|N|[QUY]hof|Function2<String,Int,Unit>|F|F|T[<]|[QUY]hof2|Function2<String,Int,Unit>|F|F|T[<]|N|ApisAsyncReturn|F[<][<]
 * [API]doSthAsyncHOF|N|F|F|F|[IVO]doSthAsyncHOF|F|N|[QUY]hof|Function2<String,TestDataBean,Unit>|F|F|T[<]|N|ApisAsyncReturn|F[<][<]
 * [API]doSthAsyncHOFNullable|N|F|F|F|[IVO]doSthAsyncHOFNullable|F|N|[QUY]hofx1res|Function2<String,TestDataBean,Unit>?|F|F|T[<]|[QUY]hof2xx|Function2<String,TestDataBean,Unit>?|F|F|F[<]|[QUY]hof4xx|kotlin.Function3<String,Int,TestDataBean,TestDataBean?>?|F|F|F[<]|N|ApisAsyncReturn|F[<][<]
 * [API]doSthAsyncOpen|N|F|F|F|[IVO]doSthAsyncOpen|F|N|[QUY]uri|String|F|F|F[<]|[QUY]results|com.mars.component.detail.api.Callback?|F|F|T[<]|N|ApisAsyncReturn|F[<][<]
 * [API]doSthBean|N|F|F|F|[IVO]doSthBean|F|N|[QUY]a1|TestDataBean|F|F|F[<]|[RST]B|com.mars.component.detail.value.TestListBean[<]|Apis|F[<][<]
 * [API]doSthCompanion|N|F|F|F|[IVO]doSthCompanion|F|N|[QUY]i|Int|F|F|F[<]|[QUY]j|String|F|F|F[<]|[QUY]k|Long|F|F|F[<]|[RST]B|String[<]|com.mars.component.detail.api.AipsInCompanion.DetailCompanion|T[<][<]
 * [API]doSthHOFCompanion|N|F|F|F|[IVO]doSthHOFCompanion|T|N|[QUY]lambdaArg0|Int|F|F|F[<]|[RST]B|Int[<]|com.mars.component.detail.api.AipsInCompanion|F[<][<]
 * [API]doSthHOFTop|N|F|F|F|[IVO]doSthHOFTop|T|N|[QUY]lambdaArg0|Unit|F|F|F[<]|[RST]B|Unit[<]|com.mars.component.detail.api.AipsInTopKt|F[<][<]
 * [API]doSthMappingBean|N|F|F|F|[IVO]doSthMappingBean|F|N|[QUY]a1|TestDataMappingBean|F|F|F[<]|[RST]B|TestDataMappingBean[<]|Apis|F[<][<]
 * [API]doSthResultReceiver|N|F|F|F|[IVO]doSthResultReceiver|F|N|[QUY]result|android.os.ResultReceiver|F|F|F[<]|[RST]B|android.os.ResultReceiver[<]|com.mars.component.detail.api.ApisWithResultReceiver|F[<][<]
 * [API]doSthTestNestDataMappingBean|N|F|F|F|[IVO]doSthTestNestDataMappingBean|F|N|[QUY]bean|com.mars.component.detail.value.mapping.TestNestDataMappingBean|F|F|F[<]|[RST]B|com.mars.component.detail.value.mapping.TestNestDataMappingBean[<]|Apis|F[<][<]
 * [API]doSthTop|N|F|F|F|[IVO]doSthTop|T|N|[QUY]ints|Array<Int>|F|F|F[<]|[QUY]li|List<Int?>|F|F|F[<]|[QUY]strings|Array<String>|F|F|F[<]|[QUY]ls|List<String>|F|F|F[<]|[QUY]beans|Array<TestDataBean>|F|F|F[<]|[QUY]lb|List<TestDataBean>|F|F|F[<]|[RST]B|List<TestDataBean>[<]|com.mars.component.detail.api.AipsInTopKt|F[<][<]
 * [API]doSthVararg|N|F|F|F|[IVO]doSthVararg|F|N|[QUY]no|Int|F|F|F[<]|[QUY]varargString|*Array<String>|F|T|F[<]|N|Apis|F[<][<]
 * [API]doSthVarargHof|N|F|F|F|[IVO]doSthVarargHof|F|N|[QUY]no|Int|F|F|F[<]|[QUY]varargString|*Array<kotlin.Function5<android.app.Activity,androidx.lifecycle.ViewModelStoreOwner,androidx.lifecycle.LifecycleOwner,androidx.lifecycle.LifecycleOwner?,androidx.lifecycle.LifecycleOwner?,androidx.lifecycle.LifecycleOwner?>>|F|T|F[<]|N|Apis|F[<][<]
 * [API]doTestDataType1|N|F|T|F|[IVO]doTestDataType1|F|N|[QUY]data|Context?|F|F|F[<]|[RST]B|Context?[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType10|N|F|F|F|[IVO]doTestDataType10|F|N|[QUY]resultSuccess|Function1<Int,Unit>|F|F|F[<]|[QUY]resultSuccess2|Function1<String,Unit>|F|F|F[<]|N|TestDataTypeTask|F[<][<]
 * [API]doTestDataType11|N|F|F|F|[IVO]doTestDataType11|F|N|[QUY]resultSuccess|Function2<Int,TestNullableBean,Unit>|F|F|T[<]|[QUY]resultSuccess2|Function1<TestNullableBean?,Unit>|F|F|T[<]|N|TestDataTypeTask|F[<][<]
 * [API]doTestDataType13|N|F|T|F|[IVO]doTestDataType13|F|N|[QUY]data|List<Int?>|F|F|F[<]|[RST]B|List<Int?>[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType14|N|F|T|F|[IVO]doTestDataType14|F|N|[QUY]data|ArrayList<Int?>|F|F|F[<]|[RST]B|ArrayList<Int?>[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType15|N|F|T|F|[IVO]doTestDataType15|F|N|[QUY]data|List<TestNullableBean?>|F|F|F[<]|[RST]B|List<TestNullableBean?>[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType16|N|F|T|F|[IVO]doTestDataType16|F|N|[QUY]data|ArrayList<TestNullableBean?>|F|F|F[<]|[RST]B|ArrayList<TestNullableBean?>[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType17|N|F|T|F|[IVO]doTestDataType17|F|N|[QUY]data|List<String?>|F|F|F[<]|[RST]B|List<String?>[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType18|N|F|T|F|[IVO]doTestDataType18|F|N|[QUY]data|ArrayList<String?>|F|F|F[<]|[RST]B|ArrayList<String?>[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType19|N|F|T|F|[IVO]doTestDataType19|F|N|[QUY]data|ArrayList<kotlin.Pair<String?,Int?>>|F|F|F[<]|[RST]B|ArrayList<kotlin.Pair<String?,Int?>>[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType2|N|F|T|F|[IVO]doTestDataType2|F|N|[QUY]data|Int?|F|F|F[<]|[RST]B|Int?[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType20|N|F|T|F|[IVO]doTestDataType20|F|N|[QUY]media|kotlin.Pair<Long?,String?>|F|F|F[<]|[RST]B|List<String>?[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType21|N|F|T|F|[IVO]doTestDataType21|F|N|[QUY]data|List<TestNullableBean>|F|F|F[<]|[RST]B|List<TestNullableBean>[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType22|N|F|T|F|[IVO]doTestDataType22|F|N|[QUY]data|List<TestNullableBean>|F|F|F[<]|[RST]B|List<TestNullableBean>[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType23|N|F|T|F|[IVO]doTestDataType23|F|N|[QUY]data|Array<TestNullableBean>|F|F|F[<]|[RST]B|Array<TestNullableBean>[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType24|N|F|T|F|[IVO]doTestDataType24|F|N|[QUY]data|Map<String,TestNullableBean>|F|F|F[<]|[RST]B|Map<String,TestNullableBean>[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType25|N|F|T|F|[IVO]doTestDataType25|F|N|[QUY]data|Map<String,TestNullableBean>|F|F|F[<]|[RST]B|Map<String,TestNullableBean>[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType26|N|F|T|F|[IVO]doTestDataType26|F|N|[QUY]data|kotlin.collections.Set<TestNullableBean>|F|F|F[<]|[RST]B|kotlin.collections.Set<TestNullableBean>[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType27|N|F|T|F|[IVO]doTestDataType27|F|N|[QUY]data|kotlin.collections.Set<TestNullableBean>|F|F|F[<]|[RST]B|kotlin.collections.Set<TestNullableBean>[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType3|N|F|T|F|[IVO]doTestDataType3|F|N|[QUY]data|String?|F|F|F[<]|[RST]B|String?[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType4|N|F|T|F|[IVO]doTestDataType4|F|N|[QUY]data|TestNullableBean?|F|F|F[<]|[RST]B|TestNullableBean?[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType5|N|F|T|F|[IVO]doTestDataType5|F|N|[QUY]data|List<String>?|F|F|F[<]|[RST]B|List<String>?[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType6|N|F|T|F|[IVO]doTestDataType6|F|N|[QUY]data|Array<Long>?|F|F|F[<]|[RST]B|Array<Long>?[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType7|N|F|T|F|[IVO]doTestDataType7|F|N|[QUY]data|kotlin.LongArray?|F|F|F[<]|[RST]B|kotlin.LongArray?[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType8|N|F|T|F|[IVO]doTestDataType8|F|N|[QUY]data|kotlin.IntArray?|F|F|F[<]|[RST]B|kotlin.IntArray?[<]|TestDataTypeTask|F[<][<]
 * [API]doTestDataType9|N|F|T|F|[IVO]doTestDataType9|F|N|[QUY]data|kotlin.BooleanArray?|F|F|F[<]|[RST]B|kotlin.BooleanArray?[<]|TestDataTypeTask|F[<][<]
 * [API]doTestGenericity1|N|F|T|F|[IVO]doTestGenericity1|T|N|[QUY]data|List<TestNullableBean?>?|F|F|F[<]|[RST]B|List<TestNullableBean?>?[<]|TestGenericityTaskKt|F[<][<]
 * [API]doTestGenericity2|N|F|T|F|[IVO]doTestGenericity2|T|N|[QUY]data|LiveData<TestNullableBean?>?|F|F|F[<]|[RST]B|LiveData<TestNullableBean?>?[<]|TestGenericityTaskKt|F[<][<]
 * [API]doTestGenericity3|N|F|T|F|[IVO]doTestGenericity3|T|N|[QUY]data|Map<String,Long?>|F|F|F[<]|[RST]B|Map<String,Long?>[<]|TestGenericityTaskKt|F[<][<]
 * [API]doTestGenericity6|N|F|T|F|[IVO]doTestGenericity6|F|N|[QUY]data|Array<TestNullableBean?>?|F|F|F[<]|[RST]B|Array<TestNullableBean?>?[<]|com.mars.component.detail.test.TestGenericityTask|F[<][<]
 * [API]doTestGenericity7|N|F|T|F|[IVO]doTestGenericity7|F|N|[QUY]data|Array<Long?>?|F|F|F[<]|[RST]B|Array<Long?>?[<]|com.mars.component.detail.test.TestGenericityTask.Companion|T[<][<]
 * [API]doTestGenericity8|N|F|F|F|[IVO]doTestGenericity8|T|N|[QUY]resultSuccess|Function1<ArrayList<String>,Unit>|F|F|F[<]|N|TestGenericityTaskKt|F[<][<]
 * [API]doTestGenericity9|N|F|F|F|[IVO]doTestGenericity9|T|N|[QUY]resultSuccess|Function1<List<String>,Unit>|F|F|F[<]|N|TestGenericityTaskKt|F[<][<]
 * [API]enterprise/business/allocateTicket|N|F|F|F|[IVO]allocateTicket|F|N|[QUY]context|Context|F|F|F[<]|[QUY]shareId|Long|F|F|F[<]|[QUY]count|Int|F|F|F[<]|[QUY]packetType|Int|F|F|F[<]|[RST]B|LiveData<List<String>>?[<]|com.mars.component.detail.api.ApisBadCase|F[<][<]
 * [API]fragment/page1|androidx.fragment.app.Fragment|F|T|T|[IVO]com.mars.component.detail.ui.FirstPageFragment|F|N|E|[RST]B|com.mars.component.detail.ui.FirstPageFragment[<]|com.mars.component.detail.ui.FirstPageFragment|F[<][<]
 * [API]get-a-lot-of-common-instance|N|F|T|T|[IVO]getALotOfObjectTest|T|N|[QUY]i|Int|F|F|F[<]|[RST]B|List<ObjectTest>[<]|ObjectTestKt|F[<][<]
 * [API]get-a-lot-of-common-instance-null|N|F|T|T|[IVO]getALotOfObjectTestNull|T|N|[QUY]i|Int|F|F|F[<]|[RST]B|List<ObjectTest?>?[<]|ObjectTestKt|F[<][<]
 * [API]get-a-other-common-instance|N|F|F|F|[IVO]getAOtherObjectTest|T|N|[QUY]i|Int|F|F|F[<]|[RST]B|ObjectTest[<]|ObjectTestKt|F[<][<]
 * [API]get-a-other-common-instance-null|N|F|F|F|[IVO]getAOtherObjectTestNull|T|N|[QUY]i|Int|F|F|F[<]|[RST]B|ObjectTest?[<]|ObjectTestKt|F[<][<]
 * [API]getFilesMeta|N|F|F|F|[IVO]getFilesMeta|F|N|[QUY]context|Context?|F|F|F[<]|[QUY]path|String?|F|F|F[<]|[QUY]callback|com.mars.component.detail.api.GetFileMetaCallback|F|F|T[<]|N|com.mars.component.detail.api.ApisBadCase|F[<][<]
 * [API]live-data-bean-list/get|N|F|F|F|[IVO]getSthLiveDataBeanList|F|N|E|[RST]B|LiveData<List<com.mars.component.detail.value.TestListBean?>>[<]|com.mars.component.detail.api.ApisWithLiveData|F[<][<]
 * [API]live-data-bean/get|N|F|F|F|[IVO]getSthLiveDataBean|F|N|E|[RST]B|LiveData<TestDataBean>[<]|com.mars.component.detail.api.ApisWithLiveData|F[<][<]
 * [API]live-data/get|N|F|F|F|[IVO]getSthLiveData|F|N|E|[RST]B|LiveData<String>[<]|com.mars.component.detail.api.ApisWithLiveData|F[<][<]
 * [API]object/get|N|F|T|T|[IVO]doSthInObject3|F|[OBJ]ObjectTest|create-obj2|do-sth-in-|do-sth-in-1|object/get[<]|[QUY]objectInstance|ObjectTest|F|F|F[<]|[QUY]v1|String|F|F|F[<]|[QUY]v2|Int?|F|F|F[<]|[QUY]v3|Int?|F|F|F[<]|N|ObjectTest|F[<][<]
 * [API]property/companion|N|F|F|F|[IVO]propertyCompanion|T|N|E|[RST]B|String[<]|com.mars.component.detail.api.AipsInCompanion|F[<][<]
 * [API]1.0/property/property|N|F|F|F|[IVO]property|F|N|E|[RST]B|String[<]|Apis|F[<][<]
 * [API]property/top|N|F|F|F|[IVO]propertyTop|T|N|E|[RST]B|String[<]|com.mars.component.detail.api.AipsInTopKt|F[<][<]
 * [API]send-back-a-lot-of-common-instance|N|F|T|T|[IVO]senBackALotOfObjectTest|T|N|[QUY]objs|Map<String,ObjectTest>|F|F|F[<]|N|ObjectTestKt|F[<][<]
 * [API]send-back-a-lot-of-common-instance-null|N|F|T|T|[IVO]senBackALotOfObjectTesNull|T|N|[QUY]objs|Map<String?,ObjectTest?>?|F|F|F[<]|N|ObjectTestKt|F[<][<]
 * [API]send-back-common-instance|N|F|F|F|[IVO]senBackObjectTest|T|N|[QUY]obj|ObjectTest|F|F|F[<]|N|ObjectTestKt|F[<][<]
 * [API]send-back-common-instance-null|N|F|F|F|[IVO]senBackObjectTestNull|T|N|[QUY]obj|ObjectTest?|F|F|F[<]|N|ObjectTestKt|F[<][<]
 * [API]sth-navigation-only/{uri}|N|T|F|F|[IVO]getSthNavigationOnly|F|N|[QUY]uri|String|F|F|F[<]|[RST]B|String[<]|Apis|F[<][<]
 * [API]sth/{id}/a-{name}?code1={code1}&code2={code2}|N|F|F|F|[IVO]getSth|F|N|[QUY]id|String|F|F|F[<]|[QUY]name|String|F|F|F[<]|[QUY]code1|String|F|F|F[<]|[QUY]code2|String|F|F|F[<]|[RST]B|String[<]|Apis|F[<][<]
 * [API]test-bean/create|N|F|F|F|[IVO]com.mars.component.detail.value.TestCreateBean|F|N|[QUY]d1|Int?|F|F|F[<]|[QUY]d2|String?|F|F|F[<]|[RST]B|com.mars.component.detail.value.TestCreateBean[<]|com.mars.component.detail.value.TestCreateBean|F[<][<]
 * [API]test-name|N|F|T|T|[IVO]testName|F|N|E|N|TestDefaultPathTask|F[<][<]
 * [API]test-name-string|N|F|T|T|[IVO]testNameString|F|N|E|[RST]B|String[<]|TestDefaultPathTask|F[<][<]
 * [API]testGenericity4|N|F|T|F|[IVO]testGenericity4|F|N|E|[RST]B|List<String?>[<]|com.mars.component.detail.test.TestGenericityTask|F[<][<]
 * [API]testGenericity5|N|F|T|F|[IVO]testGenericity5|F|N|[QUY]lambdaArg0|List<String?>?|F|F|F[<]|[RST]B|List<String?>?[<]|com.mars.component.detail.test.TestGenericityTask|F[<][<]
 * [API]test_name_up|N|F|T|T|[IVO]TEST_NAME_UP|F|N|E|N|TestDefaultPathTask|F[<][<]
 * [API]view/get|N|F|F|F|[IVO]getView|F|N|[QUY]context|Context|F|F|F[<]|[RST]B|android.view.View?[<]|Apis|F[<][<]
 * [ACT]activity/page1|B|F|T|com.mars.component.detail.ui.FirstPageActivity|[PRT]key_5_bean|N|TestDataBean?[<]|[PRT]key_1_pa|N|TestCompanionBean?[<]|[PRT]key_2_pa_ar|N|Array<TestCompanionBean>?[<]|[PRT]key_c_pa_li|N|List<TestCompanionBean>?[<]|[PRT]key_3_int|N|Int[<]|[PRT]uri|N|String?[<]|[PRT]key_4_strs|serializable|List<String>?[<]|[PRT]key_a_ints|N|List<Int>?[<][<]
 * [ACT]activity/page1_from_detail|B|F|T|com.mars.component.detail.ui.FirstPageActivity|[PRT]key_5_bean|N|TestDataBean?[<]|[PRT]key_int_for_detail|N|Int?[<]|[PRT]key_int_for_all|N|Int?[<]|[PRT]key_1_pa|N|TestCompanionBean?[<]|[PRT]key_2_pa_ar|N|Array<TestCompanionBean>?[<]|[PRT]key_c_pa_li|N|List<TestCompanionBean>?[<]|[PRT]key_3_int|N|Int[<]|[PRT]uri|N|String?[<]|[PRT]key_4_strs|serializable|List<String>?[<]|[PRT]key_a_ints|N|List<Int>?[<][<]
 * [ACT]activity/page1_from_home|B|F|T|com.mars.component.detail.ui.FirstPageActivity|[PRT]key_5_bean|N|TestDataBean?[<]|[PRT]key_int_for_home|N|Int?[<]|[PRT]key_int_for_all|N|Int?[<]|[PRT]key_1_pa|N|TestCompanionBean?[<]|[PRT]key_2_pa_ar|N|Array<TestCompanionBean>?[<]|[PRT]key_c_pa_li|N|List<TestCompanionBean>?[<]|[PRT]key_3_int|N|Int[<]|[PRT]uri|N|String?[<]|[PRT]key_4_strs|serializable|List<String>?[<]|[PRT]key_a_ints|N|List<Int>?[<][<]
 * [ACT]activity/page2/{key_str1}/{key_str2}/{key_str3}|B|F|T|com.mars.component.detail.ui.SecondPageActivity|[PRT]key_str1|N|String?[<]|[PRT]key_str2|N|String?[<]|[PRT]key_str3|N|String?[<][<]
 * [EVT]LifeCycleEvent_Destroy|B|[IVO]destroy|F|N|E|[RST]B|Unit[<]|com.mars.component.detail.event.EventsWithLambda|F[<][<]
 * [EVT]LifeCycleEvent_Destroy|B|[IVO]onDestroy|F|N|[QUY]context|Context|F|F|F[<]|[QUY]parameter1|String|F|F|F[<]|N|com.mars.component.detail.event.Events|F[<][<]
 * [EVT]LifeCycleEvent_Init|B|[IVO]init|F|N|E|[RST]B|Unit[<]|com.mars.component.detail.event.EventsWithLambda|F[<][<]
 * [EVT]LifeCycleEvent_Init|B|[IVO]onInit|F|N|[QUY]context|Context|F|F|F[<]|[QUY]parameter1|String|F|F|F[<]|N|com.mars.component.detail.event.Events|F[<][<]
 * [EVT]MY_CALLBACK_BEAN|events-by-instance|[IVO]onCallbackBean|F|[EIN]events-by-instance|[IVO]provideEve|T|N|[QUY]rubikInstanceBool|Boolean|F|F|F[<]|[RST]B|EventsByInstance[<]|EventsKt|F[<][<]|[QUY]rubikInstanceBool|Boolean|F|F|F[<]|[QUY]arg1|Any|F|F|F[<]|[QUY]bean|TestDataBean|F|F|F[<]|N|EventsByInstance|F[<][<]
 * [EVT]MY_CALLBACK_RES|events-by-instance|[IVO]onCallbackRes|F|[EIN]events-by-instance|[IVO]provideEve|T|N|[QUY]rubikInstanceBool|Boolean|F|F|F[<]|[RST]B|EventsByInstance[<]|EventsKt|F[<][<]|[QUY]rubikInstanceBool|Boolean|F|F|F[<]|[QUY]arg1|Any|F|F|F[<]|[QUY]result|Function1<Int,Unit>|F|F|T[<]|N|EventsByInstance|F[<][<]
 * [EVT]MY_CALLBACK_RES_BEAN|events-by-instance|[IVO]onCallbackResBean|F|[EIN]events-by-instance|[IVO]provideEve|T|N|[QUY]rubikInstanceBool|Boolean|F|F|F[<]|[RST]B|EventsByInstance[<]|EventsKt|F[<][<]|[QUY]rubikInstanceBool|Boolean|F|F|F[<]|[QUY]arg1|Any|F|F|F[<]|[QUY]result|Function1<TestDataBean,Unit>|F|F|T[<]|N|EventsByInstance|F[<][<]
 * [EVT]MY_DESTROY|events-by-instance|[IVO]onDestroy|F|[EIN]events-by-instance|[IVO]provideEve|T|N|[QUY]rubikInstanceBool|Boolean|F|F|F[<]|[RST]B|EventsByInstance[<]|EventsKt|F[<][<]|[QUY]rubikInstanceBool|Boolean|F|F|F[<]|[QUY]arg1|Any|F|F|F[<]|[QUY]arg2|Any|F|F|F[<]|[QUY]arg3|Any|F|F|F[<]|N|EventsByInstance|F[<][<]
 * [EVT]MY_INIT|events-by-instance|[IVO]onInit|F|[EIN]events-by-instance|[IVO]provideEve|T|N|[QUY]rubikInstanceBool|Boolean|F|F|F[<]|[RST]B|EventsByInstance[<]|EventsKt|F[<][<]|[QUY]rubikInstanceBool|Boolean|F|F|F[<]|[QUY]arg1|Any|F|F|F[<]|[QUY]arg2|Any|F|F|F[<]|[QUY]arg3|Any|F|F|F[<]|N|EventsByInstance|F[<][<]
 * [VLE]TestCompanionBean|E|E|[FLD]Int|E|0|F[<]|[FLD]String|E|N|F[<]|[FLD]Int|E|33|T[<]|[FLD]String|E|"CONST_COMP"|T[<]|[FLD]kotlin.Float|E|0.1F|T[<][<]
 * [VLE]com.mars.component.detail.value.TestCreateBean|E|E|[FLD]Int?|[ANT]SerializedName|value = "data1"[<]|N|F[<]|[FLD]String?|[ANT]SerializedName|value = "data2"[<]|N|F[<][<]
 * [VLE]TestDataBean|E|E|[FLD]Int?|[ANT]SerializedName|value = "data1"[<]|N|F[<]|[FLD]String?|[ANT]SerializedName|value = "data2"[<]|N|F[<][<]
 * [VLE]com.mars.component.detail.value.TestListBean|E|E|[FLD]Int|[ANT]SerializedName|value = "data1"[<]|0|F[<]|[FLD]List<String>?|E|N|F[<][<]
 * [VLE]TestNullableBean|E|E|[FLD]Int?|[ANT]SerializedName|value = "ddd111"[<]|N|F[<]|[FLD]String?|[ANT]SerializedName|value = "ddd222"[<]|N|F[<][<]
 * [VLE]TestParcelizeBean|android.os.Parcelable|[ANT]kotlinx.android.parcel.Parcelize|E[<]|[FLD]Int?|E|N|F[<]|[FLD]String?|E|N|F[<][<]
 * [VLE]com.mars.component.detail.value.TestSerializableBean|E|E|[FLD]Int?|[ANT]SerializedName|value = "data1"[<]|N|F[<]|[FLD]String?|[ANT]SerializedName|value = "data2"[<]|N|F[<][<]
 * [VLE]TestDataMappingBean|E|E|[FLD]Int?|E|N|F[<]|[FLD]String?|E|N|F[<][<]
 * [VLE]com.mars.component.detail.value.mapping.TestNestDataMappingBean|E|E|[FLD]TestDataBean?|E|N|F[<]|[FLD]TestDataMappingBean?|E|N|F[<]|[FLD]com.mars.component.detail.value.mapping.TestNestDataMappingBean?|E|N|F[<]|[FLD]List<TestDataBean>?|E|N|F[<]|[FLD]List<TestDataMappingBean>?|E|N|F[<][<]
 * [OBJ]ObjectTest|create-obj2|do-sth-in-|do-sth-in-1|object/get[<]
 * [OBJ]ObjectTestCreate|create-object-test-create-instance|create-object-test-create-instance-by-v1-v2-v3-v4|do-sth-create-common-instance[<]
 * [CBO]com.mars.component.detail.callback.CallbackTestDefault|[IVF]callbackDefault1|[QUY]int1|Int|F|F|F[<]|N|F[<]|[IVF]callbackDefault2|[QUY]bean|TestDataBean?|F|F|F[<]|N|F[<][<]
 * [CBO]com.mars.component.detail.callback.CallbackTestInterface|[IVF]callbackInt|[QUY]i|Int|F|F|F[<]|N|F[<]|[IVF]callbackBean|[QUY]bean|TestDataBean|F|F|F[<]|N|F[<]|[IVF]callbackBeanReBean|[QUY]bean|TestDataBean?|F|F|F[<]|N|F[<][<]
 * [MAP]Any->kotlin.Any[<]
 * [MAP]Apis->com.mars.component.detail.api.Apis[<]
 * [MAP]ApisAsyncReturn->com.mars.component.detail.api.ApisAsyncReturn[<]
 * [MAP]ApisBigData->com.mars.component.detail.api.ApisBigData[<]
 * [MAP]Array->kotlin.Array[<]
 * [MAP]ArrayList->kotlin.collections.ArrayList[<]
 * [MAP]Boolean->kotlin.Boolean[<]
 * [MAP]Context->android.content.Context[<]
 * [MAP]EventsByInstance->com.mars.component.detail.event.EventsByInstance[<]
 * [MAP]EventsKt->com.mars.component.detail.event.EventsKt[<]
 * [MAP]Function1->kotlin.Function1[<]
 * [MAP]Function2->kotlin.Function2[<]
 * [MAP]Int->kotlin.Int[<]
 * [MAP]List->kotlin.collections.List[<]
 * [MAP]LiveData->androidx.lifecycle.LiveData[<]
 * [MAP]Long->kotlin.Long[<]
 * [MAP]Map->kotlin.collections.Map[<]
 * [MAP]ObjectTest->com.mars.component.detail.objekt.ObjectTest[<]
 * [MAP]ObjectTestCreate->com.mars.component.detail.objekt.ObjectTestCreate[<]
 * [MAP]ObjectTestKt->com.mars.component.detail.objekt.ObjectTestKt[<]
 * [MAP]SerializedName->com.google.gson.annotations.SerializedName[<]
 * [MAP]String->kotlin.String[<]
 * [MAP]Task->com.mars.component.detail.api.Task[<]
 * [MAP]TestCompanionBean->com.mars.component.detail.value.TestCompanionBean[<]
 * [MAP]TestDataBean->com.mars.component.detail.value.TestDataBean[<]
 * [MAP]TestDataMappingBean->com.mars.component.detail.value.mapping.TestDataMappingBean[<]
 * [MAP]TestDataTypeTask->com.mars.component.detail.test.TestDataTypeTask[<]
 * [MAP]TestDefaultPathTask->com.mars.component.detail.test.TestDefaultPathTask[<]
 * [MAP]TestGenericityTaskKt->com.mars.component.detail.test.TestGenericityTaskKt[<]
 * [MAP]TestNullableBean->com.mars.component.detail.value.TestNullableBean[<]
 * [MAP]TestParcelizeBean->com.mars.component.detail.value.TestParcelizeBean[<]
 * [MAP]Unit->kotlin.Unit[<]
 * [MAP]create-obj2->create-object-test-instance[<]
 * [MAP]do-sth-in-->do-sth-in-common-instance[<]
 * [MAP]do-sth-in-1->do-sth-in-common-instance-parameter[<]
 * [MAP]object/get->object/get/do-sth-in-common-ins[<]
 * [MAP]provideEve->provideEventsInstance[<] 
 * version: 0.0.1-DEV
 */
@RGenerated(
  kind = "aggregate_Id",
  by = "rubik-kapt:1.10.0.0-K1_5-LOCAL",
  version = "0.0.1-DEV"
)
@RAggregate(
  uri = "demo://com.mars.rubik-test.detail",
  version = "0.0.1-DEV",
  token = "23234bceab3ea7b3508a03d64e91cf41"
)
@Keep
class DetailAggregateId :
    RAggregateId(uri = "demo://com.mars.rubik-test.detail", version = "0.0.1-DEV", token = "23234bceab3ea7b3508a03d64e91cf41")
