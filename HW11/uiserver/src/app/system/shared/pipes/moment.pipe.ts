import {Pipe, PipeTransform} from '@angular/core';
import * as moment from 'moment';

@Pipe({
  name: 'appmoment'
})
export class MomentPipe implements PipeTransform {
  transform(value: string, formatFrom: string, formatTo: string = 'MMMM Do YYYY, HH:mm'): string {
    moment.locale('ru');
    return moment.utc(value, formatFrom).format(formatTo);
  }

}
