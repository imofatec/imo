import React, { useState } from "react";
import { View, Text, Pressable, ActivityIndicator } from "react-native";
import FormInput from "../components/inputs/formInput";
import { router } from "expo-router";
import { Ionicons } from '@expo/vector-icons';
import { loginSchema } from "../schemas/login";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm, Controller } from "react-hook-form";
import { loginRequest } from "../requests/user/loginRequest";
import Toast from "react-native-toast-message";



export default function Login() {
  const [loading, setLoading] = useState(false);
  const { control, handleSubmit } = useForm({
    resolver: zodResolver(loginSchema)
  })

  async function onSubmit(data) {
    setLoading(true);
    const response = await loginRequest(data);
    setLoading(false);
    if (!response.success) {
      Toast.show({
        type: "error",
        text1: "Erro ao fazer login",
        text2: response.error,
        position: "bottom",
      });
      return;
    }
    router.push("/home");
  }


  return (
    <View className="flex-1 justify-center px-6 bg-custom-primary">

      <Text className='text-white text-6xl text-center mb-10'>
        <Ionicons name="caret-forward-outline" size={56} color="white" />IMO
      </Text>

      <FormInput control={control} name="email" label="Email" placeholder="joaosilva@gmail.com" autoCapitalize="none" keyboardType="email-address" />
      <FormInput control={control} name="password" label="Senha" placeholder="batatinha123" autoCapitalize="none" secureTextEntry />

      <Pressable className="bg-white py-3 rounded-full mb-6" onPress={handleSubmit(onSubmit)}>
        {loading ? (
          <ActivityIndicator size="small" color="#000" />
        ) : (
          <Text className="text-black text-2xl text-center font-bold">Entrar</Text>
        )}
      </Pressable>

      <Pressable onPress={() => router.push("/register")}>
        <Text className="text-gray-500 text-center mb-6">
          Esqueceu sua senha?{" "}</Text>
      </Pressable>


      <Pressable onPress={() => router.push("/register")}>
        <Text className="text-gray-300 text-center">
          Não tem conta?{" "}
          <Text className="text-blue-400 font-bold">Cadastre-se</Text>
        </Text>
      </Pressable>
    </View>
  );
}
