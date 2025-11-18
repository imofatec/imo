import React, { useState, useEffect } from "react";
import { KeyboardAvoidingView, Text, Pressable, Platform, View, ActivityIndicator } from "react-native";
import FormInput from "../../components/inputs/formInput";
import { router } from "expo-router";
import { editUserSchema } from "../../schemas/editUser";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm, Controller } from "react-hook-form";
import ProfileImagePicker from "../../components/settings/profileImagePicker";
import { ScrollView } from "react-native";
import { Ionicons } from '@expo/vector-icons'
import { useCurrentUser } from "../../hooks/useCurrentUser"
import { editUserRequest } from "../../requests/user/editUserRequest";
import { editUserPfpRequest } from "../../requests/user/editUserPfpRequest";
import Toast from "react-native-toast-message";

export default function settings() {
  const [loading, setLoading] = useState(false);
  const [imageUri, setImageUri] = useState(null);

  const { user, urlImage, loading: userLoading, error, refetch } = useCurrentUser();

  const { control, handleSubmit, reset } = useForm({
    resolver: zodResolver(editUserSchema)
  })

  async function onEditUser(data) {
    setLoading(true);
    const response = await editUserRequest(data);
    setLoading(false);

    if (!response.success) {
      Toast.show({
        type: "error",
        text1: "Erro ao editar o usuario",
        text2: response.error,
        position: "bottom",
      });
      return;
    }
    await refetch();
    reset();
  }

  async function onSavePhoto() {
    if (!imageUri) return alert("Selecione uma imagem primeiro!");
    const response = await editUserPfpRequest(imageUri);
    if (!response.success) {
      Toast.show({
        type: "error",
        text1: "Erro ao enviar uma foto",
        text2: response.error,
        position: "bottom",
      });
      return;
    }
    await refetch();
    setImageUri(null);
    alert("Foto de perfil atualizada com sucesso!");
  }

  return (
    <KeyboardAvoidingView
      className="flex-1 bg-custom-primary"
      behavior={Platform.OS === "android" ? "padding" : "height"}
    >
      <ScrollView className="flex-1 px-6 bg-custom-primary"
        contentContainerStyle={{ justifyContent: "center", flexGrow: 1 }}>

        <Text className='text-white text-4xl text-center mb-3'>
          Editar Perfil
        </Text>

        <View className="flex-1 justify-center items-center gap-3">
          <ProfileImagePicker imageUri={imageUri} onImagePicked={setImageUri} />
          <Pressable className="flex-row items-center gap-4" onPress={onSavePhoto}>
            <Ionicons name="download-outline" size={20} color="green" />
            <Text className="text-green-500 text-lg">Salvar foto</Text>
          </Pressable>
        </View>

        <Text className='text-white text-xl text-center mb-5 mt-5'>
          Nome do usuário
        </Text>

        <View>
          <FormInput control={control} name="email" label="Email" placeholder={user?.email} autoCapitalize="none" keyboardType="email-address" />
          <FormInput control={control} name="user" label="Usuário" placeholder={user?.name} autoCapitalize="none" />
          <FormInput control={control} name="password" label="Senha" placeholder="*********" autoCapitalize="none" secureTextEntry />
          <FormInput control={control} name="confirmPassword" label="Confirmar senha" placeholder="*********" autoCapitalize="none" secureTextEntry />
          <Pressable className="bg-white py-3 rounded-full mt-6" onPress={handleSubmit(onEditUser)}>
            {loading ? (
              <ActivityIndicator size="small" color="#000" />
            ) : (
              <Text className="text-black text-2xl text-center font-bold">Confirmar</Text>
            )}
          </Pressable>
        </View>

      </ScrollView>
    </KeyboardAvoidingView>
  );
}