/*
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMealRepository(
        mealApiService: MealApiService,
        mealDao: MealDao
    ): MealRepository {
        return DefaultMealRepository(mealApiService, mealDao)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideMealDao(database: MealDatabase): MealDao {
        return database.mealDao()
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MealDatabase {
        return MealDatabase.getDatabase(context)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideMealApiService(): MealApiService {
        // Provide your MealApiService instance
    }
}*/
