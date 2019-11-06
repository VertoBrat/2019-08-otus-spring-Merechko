import {NgModule} from '@angular/core';
import {LoaderComponent} from './system/shared/loader/loader.component';

@NgModule({
  declarations: [LoaderComponent],
  exports:[LoaderComponent]
})
export class CustomModule {

}
