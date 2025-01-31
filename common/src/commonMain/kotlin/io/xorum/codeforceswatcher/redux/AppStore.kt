package io.xorum.codeforceswatcher.redux

import com.squareup.sqldelight.db.SqlDriver
import io.xorum.codeforceswatcher.features.auth.IFirebaseController
import io.xorum.codeforceswatcher.redux.middlewares.appMiddleware
import io.xorum.codeforceswatcher.redux.middlewares.toastMiddleware
import io.xorum.codeforceswatcher.redux.reducers.appReducer
import io.xorum.codeforceswatcher.util.IAnalyticsController
import io.xorum.codeforceswatcher.util.PersistenceController
import tw.geothings.rekotlin.Store

lateinit var sqlDriver: SqlDriver

val persistenceController = PersistenceController()
lateinit var analyticsController: IAnalyticsController
lateinit var firebaseController: IFirebaseController
var pushToken: String? = null

lateinit var getLang: () -> String

val store by lazy {
    Store(
        reducer = ::appReducer,
        state = persistenceController.fetchAppState(),
        middleware = listOf(appMiddleware, toastMiddleware)
    )
}
