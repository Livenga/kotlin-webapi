package tokyo.unionet.sof.data

import com.fasterxml.jackson.annotation.JsonProperty



data class StackOverflowResponseBase(
    @JsonProperty("has_more") val hasMore: Boolean,
    @JsonProperty("quota_max") val quotaMax: Long,
    @JsonProperty("quota_remaining") val quotaRemaining: Long
)

data class StackOverflowResponseTag(
    @JsonProperty("has_more") val hasMore: Boolean,
    @JsonProperty("quota_max") val quotaMax: Long,
    @JsonProperty("quota_remaining") val quotaRemaining: Long,
    @JsonProperty("items") val items: Array<StackOverflowTag>
)

data class StackOverflowTag(
    @JsonProperty("count") val count: Long,
    @JsonProperty("has_synonyms") val hasSynonyms: Boolean,
    @JsonProperty("is_moderator_only") val isModeratorOnly: Boolean,
    @JsonProperty("is_required") val isRequired: Boolean,
    @JsonProperty("name") val name: String
    //@JsonProperty("user_id") val userId: String? = null
)
