
package id.paniclabs.arch.common.router;

import id.paniclabs.arch.injection.scope.PerActivityScope;

import dagger.Component;

@PerActivityScope
@Component(modules = RouterModule.class)
public interface RouterComponent {

    Router provideRouter();
}
