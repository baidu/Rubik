package rubik.generate.aggregate.demo_com_mars_rubik_test_home

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
 * context uri: [demo://com.mars.rubik-test.home]
 * version: 0.1.3
 */
@RGenerated(
  kind = "aggregate",
  by = "rubik-kapt:1.9.0.1.T-K1_3-LOCAL",
  version = "0.1.3"
)
@Keep
class HomeAggregate : Aggregatable {
  override fun onEvent(msg: String, queries: Queries) {
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
    queries: Queries,
    results: List<Results>
  ) {
    when {
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
    override val URI: String = "demo://com.mars.rubik-test.home"

    override val DEPENDENCIES: List<String> = listOf()

    override val EVENT_MSGS: List<String> = listOf("LifeCycleEvent_Init","LifeCycleEvent_Destroy")

    override val CREATOR: Function0<Aggregatable> = {HomeAggregate()}
  }
}
