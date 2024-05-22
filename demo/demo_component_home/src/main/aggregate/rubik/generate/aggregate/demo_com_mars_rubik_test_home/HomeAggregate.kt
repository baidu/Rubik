package rubik.generate.aggregate.demo_com_mars_rubik_test_home

import androidx.annotation.Keep
import com.mars.component.home.api.TestOne
import com.mars.component.home.event.Events
import com.rubik.annotations.source.RAggregate
import com.rubik.annotations.source.RGenerated
import com.rubik.context.Aggregatable
import com.rubik.context.AggregateFactory
import com.rubik.identity.RAggregateId
import com.rubik.route.mapping.castToTypeOfT
import com.rubik.route.mapping.mapToType
import com.rubik.route.mapping.toTypeOfT
import kotlin.Function0
import kotlin.String
import kotlin.collections.List
import rubik.generate.context.demo_com_mars_rubik_test_home.HomeRouteActions
import com.rubik.activity.Launcher as RubikLauncher
import com.rubik.route.Queries as RubikQueries
import com.rubik.route.ResultGroups as RubikResultGroups
import com.rubik.route.Result as RubikResult
import com.rubik.router.uri.Path as RubikPath

/**
 * aggregate router function and router event of Rubik Context.
 *
 * uri: [demo://com.mars.rubik-test.home] 
 * version: 0.0.2
 */
@RGenerated(
  kind = "aggregate",
  by = "rubik-kapt:1.10.0.0-K1_5-LOCAL",
  version = "0.0.2"
)
@Keep
class HomeAggregate : Aggregatable, HomeRouteActions {
  override fun onEvent(msg: String, queries: RubikQueries) {
    when (msg){
      "LifeCycleEvent_Init" ->  {
        // com.mars.component.home.event.Events.myInit
        // - resultType:
        // --- null
        Events().myInit()}
      "MY" ->  {
        // com.mars.component.home.event.Events.myDestory
        // - resultType:
        // --- null
        Events().myDestory()}
      "LifeCycleEvent_Destroy" ->  {
        // com.mars.component.home.event.Events.myDestory
        // - resultType:
        // --- null
        Events().myDestory()}
      else -> { }
    }
  }

  override fun onRoute(
    path: String,
    queries: RubikQueries,
    results: RubikResultGroups
  ) {
    when {
      "getName" == path ->  {
        getName().apply {
          results.set(0, RubikResult(this))
        }
      }
      "getName1" == path ->  {
        getName1().apply {
          results.set(0, RubikResult(this))
        }
      }
      else -> { throw com.rubik.route.exception.BadPathOrVersionException(path) }
    }
  }

  override fun getName(): String? {
    // com.mars.component.home.api.TestOne.getName
    // - resultType:
    // --- kotlin.String
    return TestOne().getName() /* -> NO NEED TO MAPPING :[kotlin.String] */ 
  }

  override fun getName1(): String? {
    // com.mars.component.home.api.TestOne.getName1
    // - resultType:
    // --- kotlin.String
    return TestOne().getName1() /* -> NO NEED TO MAPPING :[kotlin.String] */ 
  }

  @RGenerated(
    kind = "aggregate_companion",
    by = "rubik-kapt:1.10.0.0-K1_5-LOCAL",
    version = "0.0.2"
  )
  @Keep
  companion object : AggregateFactory() {
    override val URI: String = "demo://com.mars.rubik-test.home"

    override val EVENT_MSGS: List<String> = listOf(
        "LifeCycleEvent_Init",
        "MY",
        "LifeCycleEvent_Destroy"
        )

    override val CREATOR: Function0<Aggregatable> = {HomeAggregate()}
  }
}

/**
 * generated Rubik AggregateId.
 *
 * uri: [demo://com.mars.rubik-test.home] 
 * originalToken: 
 * [API]getName|N|F|T|T|[IVO]getName|F|N|E|[RST]B|kotlin.String[<]|com.mars.component.home.api.TestOne|F[<][<]
 * [API]getName1|N|F|T|T|[IVO]getName1|F|N|E|[RST]B|kotlin.String[<]|com.mars.component.home.api.TestOne|F[<][<]
 * [EVT]LifeCycleEvent_Destroy|B|[IVO]myDestory|F|N|E|N|com.mars.component.home.event.Events|F[<][<]
 * [EVT]LifeCycleEvent_Init|B|[IVO]myInit|F|N|E|N|com.mars.component.home.event.Events|F[<][<]
 * [EVT]MY|B|[IVO]myDestory|F|N|E|N|com.mars.component.home.event.Events|F[<][<] 
 * version: 0.0.2
 */
@RGenerated(
  kind = "aggregate_Id",
  by = "rubik-kapt:1.10.0.0-K1_5-LOCAL",
  version = "0.0.2"
)
@RAggregate(
  uri = "demo://com.mars.rubik-test.home",
  version = "0.0.2",
  token = "271a57ab84a08d315a9eb178127a6776"
)
@Keep
class HomeAggregateId :
    RAggregateId(uri = "demo://com.mars.rubik-test.home", version = "0.0.2", token = "271a57ab84a08d315a9eb178127a6776")
