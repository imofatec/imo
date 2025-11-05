import AsyncStorage from "@react-native-async-storage/async-storage";
import { baseURL } from "../api/enviroment";
import { downloadAsync, documentDirectory, readAsStringAsync, deleteAsync } from "expo-file-system/legacy";
import * as Sharing from "expo-sharing";
import { Alert } from "react-native";
import { safeAwait } from "../lib/safeAwait";

export async function downloadCertificate(courseId, setIsLoading) {
  setIsLoading(true);
  const token = await AsyncStorage.getItem("token");
  const fileName = `certificado_${courseId}_${Date.now()}.pdf`;
  const fileUri = `${documentDirectory}${fileName}`;

  const [downloadError, downloadResult] = await safeAwait(
    downloadAsync(
      `${baseURL}/api/certificate/issue/${courseId}`,
      fileUri,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
  );
  if (downloadError) {
    Alert.alert("Erro", "Não foi possível baixar o certificado");
    setIsLoading(false);
    return;
  }
  if (downloadResult?.status !== 200) {
    let errorMessage = "Falha ao gerar o certificado";
    
    try {
      const fileContent = await readAsStringAsync(downloadResult.uri);
      const errorData = JSON.parse(fileContent);
      errorMessage = errorData.message || errorMessage;
    } catch (e) {
      if (downloadResult?.status === 403) {
        errorMessage = "Você ainda não completou todas as aulas deste curso";
      }
    }
    await safeAwait(deleteAsync(downloadResult.uri));
    Alert.alert("Erro", errorMessage);
    setIsLoading(false);
    return;
  }
  await Sharing.shareAsync(downloadResult.uri, {
    mimeType: "application/pdf",
    dialogTitle: "Salvar Certificado",
  });

  Alert.alert("Sucesso!", "Certificado baixado com sucesso!");
  setIsLoading(false);
}