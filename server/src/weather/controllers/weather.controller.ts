import { Controller, Get, Param } from '@nestjs/common';
import { WeatherService } from '../services/weather.service';

@Controller('weather')
export class WeatherController {
  constructor(private weatherService: WeatherService) {}

  @Get(':city')
  async getWeaterByCity(@Param('city') city: string) {
    return this.weatherService.getWeatherByCity(city);
  }

  @Get(':city/current')
  async getCurrentWeatherByCity(@Param('city') city: string) {
    return this.weatherService;
  }
}
// const response = await axios.get(apiUrl);
// const weatherData = response.data;
