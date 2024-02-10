import { Module } from '@nestjs/common';
import { WeatherController } from './weather/controllers/weather.controller';
import { WeatherService } from './weather/services/weather.service';
import { ConfigModule } from '@nestjs/config';

@Module({
  imports: [ConfigModule.forRoot()],
  controllers: [WeatherController],
  providers: [WeatherService],
})
export class AppModule {}
