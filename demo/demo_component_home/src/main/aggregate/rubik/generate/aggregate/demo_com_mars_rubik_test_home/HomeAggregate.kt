package rubik.generate.aggregate.demo_com_mars_rubik_test_home

import androidx.annotation.Keep
import com.rubik.annotations.source.RGenerated
import com.rubik.context.Aggregatable
import com.rubik.context.AggregateFactory
import com.rubik.route.mapping.caseToTypeOfT
import com.rubik.route.mapping.toTypeOfT
import kotlin.Function0
import kotlin.String
import kotlin.collections.List
import rubik.generate.context.demo_com_mars_rubik_test_home.HomeRouteActions
import com.rubik.activity.Launcher as RubikLauncher
import com.rubik.route.Queries as RubikQueries
import com.rubik.route.Result as RubikResult
import com.rubik.route.ResultGroups as RubikResultGroups
import com.rubik.router.uri.Path as RubikPath

/**
 * aggregate router function and router event of Rubik Context.
 *
 * context uri: [demo://com.mars.rubik-test.home]
 * version: 0.1.4
 */
@RGenerated(
  kind = "aggregate",
  by = "rubik-kapt:1.9.0.1.T-K1_3-LOCAL",
  version = "0.1.4"
)
@Keep
class HomeAggregate : Aggregatable, HomeRouteActions {
  override fun onEvent(msg: String, queries: RubikQueries) {
    when(msg){
      "LifeCycleEvent_Init" ->  {
        // com.mars.component.home.event.Events.myInit
        // - resultType:
        // --- null
        com.mars.component.home.event.Events().myInit()
      }
      "LifeCycleEvent_Destroy" ->  {
        // com.mars.component.home.event.Events.myDestory
        // - resultType:
        // --- null
        com.mars.component.home.event.Events().myDestory()
      }
      else -> {}
    }
  }

  override fun onRoute(
    path: String,
    queries: RubikQueries,
    results: RubikResultGroups
  ) {
    when {
      else -> { throw com.rubik.route.exception.BadPathOrVersionException(path)}
    }
  }

  @RGenerated(
    kind = "aggregate_companion",
    by = "rubik-kapt:1.9.0.1.T-K1_3-LOCAL",
    version = "0.1.4"
  )
  @Keep
  companion object : AggregateFactory() {
    override val URI: String = "demo://com.mars.rubik-test.home"

    override val DEPENDENCIES: List<String> = listOf()

    override val EVENT_MSGS: List<String> = listOf("LifeCycleEvent_Init","LifeCycleEvent_Destroy")

    override val CREATOR: Function0<Aggregatable> = {HomeAggregate()}
  }
}
