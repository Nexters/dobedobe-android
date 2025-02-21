package com.chipichipi.dobedobe.feature.dashboard.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.chipichipi.dobedobe.core.model.CharacterType
import com.chipichipi.dobedobe.feature.dashboard.R

internal sealed class CharacterResources(
    @DrawableRes val backgroundRes: Int,
    @RawRes val defaultRes: Int,
    @RawRes val reactionRes: Int,
) {
    data object Bird : CharacterResources(
        backgroundRes = R.drawable.bird_sheet_content_background,
        defaultRes = R.raw.bird01,
        reactionRes = R.raw.bird02,
    )

    data object Rabbit : CharacterResources(
        backgroundRes = R.drawable.rabbit_sheet_content_background,
        defaultRes = R.raw.rabbit01,
        reactionRes = R.raw.rabbit02,
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
