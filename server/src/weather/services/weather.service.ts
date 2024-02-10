import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import axios from 'axios';

@Injectable()
export class WeatherService {
  constructor(private configService: ConfigService) {}
  async getWeatherByCity(city: string) {
    const apiKey = this.configService.get<string>('WEATHERSTACK');
    if (city !== '') {
      const apiUrl = `http://api.weatherstack.com/current?access_key=${apiKey}&query=${city}`;
      try {
        const response = await axios.get(apiUrl);
        const weatherData = response.data;
        console.log(weatherData.current.temperature);
        return { weatherData };
      } catch (error) {
        console.log(error);
        return { error: 'City wasnt found or exist' };
      }
    }
  }

  // async getCurrentWeatherByCity(city: string) {
  //   const weatherData = await this.getWeatherByCity(city);

  //   if (weatherData && weatherData.current) {
  //     return weatherData.current;
  //   } else {
  //     return { error: 'City wasnt found or exist' };
  //   }
  // }
}
