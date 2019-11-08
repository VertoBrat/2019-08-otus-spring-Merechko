import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SystemComponent} from './system.component';
import {BookListPageComponent} from './book-page/book-list-page/book-list-page.component';
import {MainPageComponent} from './main-page/main-page.component';
import {BookEditPageComponent} from './book-page/book-edit-page/book-edit-page.component';
import {BookViewComponent} from './book-page/book-view/book-view.component';

const routes: Routes = [
  {path: '', component: SystemComponent, children: [
      {path: 'list', component: BookListPageComponent},
      {path: 'edit', component: BookEditPageComponent},
      {path: ':id', component: BookViewComponent}
    ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SystemRoutingModule {
}
