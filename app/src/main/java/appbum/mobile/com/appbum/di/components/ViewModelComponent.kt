package appbum.mobile.com.appbum.di.components

import appbum.mobile.com.appbum.di.scope.ApplicationScope
import appbum.mobile.com.appbum.ui.viewModels.SplashViewModel
import dagger.Component

@ApplicationScope
@Component(dependencies = [AppComponent::class])
interface ViewModelComponent : AppComponent {

    fun inject(splashViewModel: SplashViewModel)

}
