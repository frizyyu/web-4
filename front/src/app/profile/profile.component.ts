import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit{
  username: any = localStorage.getItem('username');
  id: any = localStorage.getItem('uid');
  data: any[] = [];
  private apiUrl = "http://localhost:8080/api/stats/get"
  constructor(private http: HttpClient, private router: Router) {}
  headers = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('authToken'),
    'Content-Type': 'application/json'
  });
  audio = new Audio();
  ngOnInit(){
    this.audio = new Audio();
    this.audio.src = "/sounds/poimi-na-etot-raz.mp3";
    this.audio.load();
    this.audio.loop = true;
    this.audio.play();

    const body = {
      action: "getAllForUser",
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
  exitClick() {
    localStorage.removeItem('username');
    localStorage.removeItem('authToken');
    localStorage.removeItem('uid');
    this.audio.pause();
    this.router.navigate(['/login'], { skipLocationChange: true });
  }
}
