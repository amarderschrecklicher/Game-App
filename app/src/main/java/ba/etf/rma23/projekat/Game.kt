package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    var platform: String?,
    var releaseDate: String?,
    @SerializedName("rating") val rating: Double?,
    var attributesImage: String?,
    var esrbRating: String?,
    var developer: String?,
    var genre: String?,
    @SerializedName("summary") val description: String?,
    var fav: String?,
    var userImpressions: List<UserImpression>? = emptyList(),
    var favorite: Boolean = false,
    @SerializedName("platforms") val platforms: List<Attributes>? = null,
    @SerializedName("release_dates") val releaseDates: List<Attributes>?=null,
    @SerializedName("cover") val imageUrl: Attributes? = null,
    @SerializedName("age_ratings") val ageRatings: List<Attributes>? = null,
    @SerializedName("involved_companies") val companies: List<Company>? = null,
    @SerializedName("genres") val genres: List<Attributes>? = null,
    )

