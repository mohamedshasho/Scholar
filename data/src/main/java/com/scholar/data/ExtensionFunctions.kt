package com.scholar.data

import com.scholar.data.model.CategoryLocal
import com.scholar.domain.model.CategoryNetwork


fun CategoryNetwork.toLocal() = CategoryLocal(id, name, image)