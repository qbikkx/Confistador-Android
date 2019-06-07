package dev.qbikkx.coreui

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import java.lang.ref.WeakReference


class LocalCiceroneHolder {
    private val containers: HashMap<String, WeakReference<Cicerone<FlowRouter>>> = HashMap()

    fun getCicerone(containerTag: String, parentRouter: Router): Cicerone<FlowRouter> {
        var cicerone: Cicerone<FlowRouter>? = containers[containerTag]?.get()
        if (cicerone == null) {
            cicerone = Cicerone.create(FlowRouter(parentRouter))!!
            containers[containerTag] = WeakReference(cicerone)
        }
        return cicerone
    }
}