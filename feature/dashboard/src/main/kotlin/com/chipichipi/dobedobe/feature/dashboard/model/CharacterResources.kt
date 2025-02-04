package com.chipichipi.dobedobe.feature.dashboard.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.chipichipi.dobedobe.core.model.CharacterType
import com.chipichipi.dobedobe.feature.dashboard.R

internal sealed class CharacterResources(
    @DrawableRes val placeholderRes: Int,
    @DrawableRes val backgroundRes: Int,
    @RawRes val defaultApngRes: Int,
    @RawRes val reactionApngRes: Int,
) {
    data object Bird : CharacterResources(
        placeholderRes = R.drawable.bird_placeholder,
        backgroundRes = R.drawable.bird_sheet_content_background,
        defaultApngRes = R.raw.bird01,
        reactionApngRes = R.raw.bird02,
    )

    data object Rabbit : CharacterResources(
        placeholderRes = R.drawable.rabbit_placeholder,
        backgroundRes = R.drawable.rabbit_sheet_content_background,
        defaultApngRes = R.raw.rabbit01,
        reactionApngRes = R.raw.rabbit02,
    )

    companion object {
        fun from(type: CharacterType): CharacterResources {
            return when (type) {
                CharacterType.Bird -> Bird
                CharacterType.Rabbit -> Rabbit
            }
        }
    }
}
