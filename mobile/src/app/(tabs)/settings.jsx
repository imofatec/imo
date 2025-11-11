import React from "react";
import { KeyboardAvoidingView, Text, Pressable, Platform } from "react-native";
import FormInput from "../../components/inputs/formInput";
import { router } from "expo-router";
import { editUserSchema } from "../../schemas/editUser";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm, Controller } from "react-hook-form";
import ProfileImagePicker from "../../components/settings/profileImagePicker";
import { ScrollView } from "react-native";

export default function EditUser() {

  const { control, handleSubmit,reset } = useForm({
    resolver: zodResolver(editUserSchema)
  })

  function onSubmit(data) {
    console.log(data);
    reset()
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

        <ProfileImagePicker></ProfileImagePicker>

        <Text className='text-white text-xl text-center mb-5 mt-5'>
          Nome do usuário
        </Text>

        <FormInput control={control} name="email" label="Email" placeholder="joao@gmail.com" autoCapitalize="none" keyboardType="email-address" />
        <FormInput control={control} name="user" label="Usuário" placeholder="JoaoSilva" autoCapitalize="none" />
        <FormInput control={control} name="password" label="Senha" placeholder="Coxinha123@" autoCapitalize="none" secureTextEntry />
        <FormInput control={control} name="confirmPassword" label="Confirmar senha" placeholder="Coxinha123@" autoCapitalize="none" secureTextEntry />


        <Pressable className="bg-white py-3 rounded-full mt-6"
          onPress={handleSubmit(onSubmit)}>

          <Text className="text-black text-2xl text-center font-bold">Confirmar</Text>
        </Pressable>
      </ScrollView>
    </KeyboardAvoidingView>
  );
}