import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-start',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './start.component.html',
  styleUrl: './start.component.css'
})
export class StartComponent implements OnInit{
  data: any[] = [];
  private apiUrl = "http://localhost:8080/api/stats/get"
  constructor(private http: HttpClient) {}
  headers = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('authToken'),
    'Content-Type': 'application/json'
  });
  ngOnInit(){
    let audio = new Audio();
    audio.src = "/sounds/the-man-behind-the-slaugter.mp3";
    audio.load();
    audio.loop = true;
    audio.play();

    const body = {
      action: "getAll",
      uid: localStorage.getItem('uid')
    };
    this.http.post<any>(this.apiUrl, body, {headers: this.headers}).subscribe(
      (response: any) => {
          this.data = response;
        },
        (error: { name: string; message: string; }) => {
          alert("Ошибка при обращении к серверу.")
          //alert("Error\n" + error.name + "\n\nMessage\n" + error.message);
      }
    );
  }
}
