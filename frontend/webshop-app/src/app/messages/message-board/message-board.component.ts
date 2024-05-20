import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-message-board',
  templateUrl: './message-board.component.html',
  styleUrls: ['./message-board.component.css']
})
export class MessageBoardComponent {
  @Input() message? : string;

}
