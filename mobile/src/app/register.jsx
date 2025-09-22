import React from "react";
import { View, Text, Pressable } from "react-native";
import FormInput from "../components/inputs/formInput";
import { registerSchema } from "../schemas/register";
import { router } from "expo-router";
import { useForm, Controller } from "react-hook-form";
import { Ionicons } from '@expo/vector-icons';
import { zodResolver } from "@hookform/resolvers/zod";


export default function Register() {

  const { control, handleSubmit } = useForm({
    resolver: zodResolver(registerSchema)
  })

  function onSubmit(data) {
    console.log(data);
    router.replace("/home");
  }

  return (
    <View className="flex-1 justify-center px-6 bg-custom-primary">

      <Text className='text-white text-6xl text-center mb-16'>
        <Ionicons name="caret-forward-outline" size={56} color="white" />IMO
      </Text>

      <FormInput control={control} name="user" label="Usuario" placeholder="JoãoSilva" />
      <FormInput control={control} name="email" label="Email" placeholder="Email" autoCapitalize="none" keyboardType="email-address" />
      <FormInput control={control} name="password" label="Senha" placeholder="Senha" autoCapitalize="none" secureTextEntry />
      <FormInput control={control} name="confirmPassword" label="Confirmar senha" placeholder="Confirmar senha" autoCapitalize="none" secureTextEntry />

      <Pressable
        className="bg-white py-3 rounded-full mb-6"
        onPress={handleSubmit(onSubmit)}
      >
        <Text className="text-black text-2xl text-center font-bold">Cadastrar</Text>
      </Pressable>

      <Pressable onPress={() => router.push("/login")}>
        <Text className="text-gray-300 text-center">
          Já possui conta?{" "}
          <Text className="text-blue-400 font-bold">Login</Text>
        </Text>
      </Pressable>
    </View>
  );
}
