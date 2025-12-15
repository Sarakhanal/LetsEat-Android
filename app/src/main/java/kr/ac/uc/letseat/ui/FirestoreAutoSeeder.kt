package kr.ac.uc.letseat.ui

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreAutoSeeder {

    private val db = FirebaseFirestore.getInstance()

    fun seedAll() {
        seedRestaurants()
        seedMenus()
        seedOwners()
        seedDrivers()
        Log.d("Seeder", "🔥 Firestore seeding completed.")
    }

    // ------------------------------------------------------
    // RESTAURANTS
    // ------------------------------------------------------
    private fun seedRestaurants() {

        val restaurants = listOf(
            mapOf(
                "id" to "bajeko",
                "name" to "Bajeko Sekuwa",
                "location" to "Eklaini Chowk",
                "ownerId" to "jqIWX5Dj5SUnykG9iNTUEW50CEVu1"
            ),
            mapOf(
                "id" to "thakali",
                "name" to "Thakali Kitchen",
                "location" to "Setu BK Chowk",
                "ownerId" to "owner_thakali"
            ),
            mapOf(
                "id" to "momo",
                "name" to "Momo Munch",
                "location" to "Nepalgunj-5",
                "ownerId" to "owner_momo"
            )
        )

        restaurants.forEach { r ->
            val id = r["id"]!!.toString()
            db.collection("restaurants")
                .document(id)
                .set(r)
        }

        Log.d("Seeder", "Restaurants seeded.")
    }

    // ------------------------------------------------------
    // MENUS
    // ------------------------------------------------------
    private fun seedMenus() {

        val menusForRestaurants = mapOf(
            "bajeko" to listOf(
                menu("buff_sekuwa", "Buff Sekuwa", 200),
                menu("chicken_sekuwa", "Chicken Sekuwa", 180),
                menu("fried_rice", "Fried Rice", 150)
            ),
            "thakali" to listOf(
                menu("thakali_set", "Thakali Set", 250),
                menu("khana_set", "Khana Set", 220)
            ),
            "momo" to listOf(
                menu("buff_momo", "Buff Momo", 120),
                menu("chicken_momo", "Chicken Momo", 130)
            )
        )

        menusForRestaurants.forEach { (restaurantId, menuList) ->
            menuList.forEach { item ->
                db.collection("restaurants")
                    .document(restaurantId)
                    .collection("menus")
                    .document(item["id"] as String)
                    .set(item)
            }
        }

        Log.d("Seeder", "Menus seeded.")
    }

    private fun menu(id: String, name: String, price: Int): Map<String, Any> {
        return mapOf(
            "id" to id,
            "name" to name,
            "price" to price,
            "imageUrl" to ""
        )
    }

    // ------------------------------------------------------
    // OWNERS
    // ------------------------------------------------------
    private fun seedOwners() {

        val owners = listOf(
            mapOf(
                "uid" to "jqIWX5Dj5SUnykG9iNTUEW50CEVu1",
                "restaurantId" to "bajeko"
            )
        )

        owners.forEach { owner ->
            db.collection("owners")
                .document(owner["uid"].toString())
                .set(owner)
        }

        Log.d("Seeder", "Owners seeded.")
    }

    // ------------------------------------------------------
    // DRIVERS
    // ------------------------------------------------------
    private fun seedDrivers() {

        val drivers = listOf(
            mapOf(
                "uid" to "VMIs3154KdTL2vg4ECTizWmXQMl2",
                "active" to true
            )
        )

        drivers.forEach { driver ->
            db.collection("drivers")
                .document(driver["uid"].toString())
                .set(driver)
        }

        Log.d("Seeder", "Drivers seeded.")
    }
}
