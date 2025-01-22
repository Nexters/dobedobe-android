package com.chipichipi.dobedobe.feature.dashboard.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.core.model.fakeGoals

class GoalPreviewParameterProvider : PreviewParameterProvider<List<Goal>> {
    override val values: Sequence<List<Goal>>
        get() = sequenceOf(
            fakeGoals(10),
            fakeGoals(3),
            fakeGoals(2),
            fakeGoals(1),
            emptyList(),
        )
}
