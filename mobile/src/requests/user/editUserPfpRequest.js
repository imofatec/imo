import AsyncStorage from "@react-native-async-storage/async-storage";
import { baseURL } from "../../api/enviroment";
import { safeAwait } from "../../lib/safeAwait";

export async function editUserPfpRequest(imageUri) {
  const formData = new FormData();

  const filename = imageUri.split("/").pop();
  const match = /\.(\w+)$/.exec(filename);
  const type = match ? `image/${match[1]}` : `image`;

  formData.append("file", {
    uri: imageUri,
    name: filename,
    type,
  });
  const token = await AsyncStorage.getItem("token");
  const [error, result] = await safeAwait(
    fetch(`${baseURL}/api/user/profile-picture`, {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${token}`,
      },
      body: formData,
    }).then(async (res) => {
      if (!res.ok) {
        const errData = await res.text();
        throw new Error(errData || `Erro ${res.status}`);
      }
      return res.json();
    })
  );

  if (error) {
    return { success: false, error: error.message };
  }

  return { success: true, data: result };
}
