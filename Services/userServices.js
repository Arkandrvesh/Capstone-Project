const db = require("../config/db");

async function getUserInfo(userId) {
  try {
    // Panggil method atau fungsi yang akan mengambil informasi pengguna dari database berdasarkan ID pengguna
    const [userInfo] = await db.execute("SELECT id, email, username FROM login WHERE id = ?", [userId]);

    if (userInfo.length === 0) {
      throw new Error("User not found");
    }

    return userInfo[0];
  } catch (error) {
    throw new Error(`Error fetching user information: ${error.message}`);
  }
}

module.exports = {
  getUserInfo,
};
