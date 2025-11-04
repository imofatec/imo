import React, { useState } from "react";
import { View, Text, TextInput, Pressable, ActivityIndicator } from "react-native";
import { useForm, Controller } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { commentSchema } from "../../schemas/comment";
import { createCommentRequest } from "../../requests/comment/createCommentRequest";

export default function CommentInput({ lessonId, parentId = null, onSuccess }) {
  const [sending, setSending] = useState(false);
  const [errorMessage, setErrorMessage] = useState(null);

  const { control, handleSubmit, reset, formState: { errors } } = useForm({
    resolver: zodResolver(commentSchema),
    defaultValues: { content: "" },
  });

  const onSubmit = async (data) => {
    try {
      setSending(true);
      setErrorMessage(null);

      const response = await createCommentRequest({
        lessonId,
        parentId,
        content: data.content,
      });
      if (!response.success) {
        setErrorMessage(response.error);
        return;
      }
      reset();
      onSuccess?.();
    } catch (err) {
      setErrorMessage(err?.message);
    } finally {
      setSending(false);
    }
  };

  return (
    <View className="px-2 pt-2 pb-4 bg-white/0">
      <Controller
        control={control}
        name="content"
        render={({ field: { onChange, value } }) => (
          <View className="bg-white/5 border border-white/10 rounded-2xl p-3">
            <TextInput
              multiline
              value={value}
              onChangeText={onChange}
              placeholder="Adicionar comentário..."
              placeholderTextColor="#A3A3A3"
              className="text-white text-base"
            />
            {errors.content && (
              <Text className="text-red-400 text-xs mt-1">{errors.content.message}</Text>
            )}
          </View>
        )}
      />

      <Pressable
        onPress={handleSubmit(onSubmit)}
        disabled={sending}
        className={`mt-3 py-3 rounded-full items-center ${sending ? "bg-white/10" : "bg-green-400"}`}
      >
        {sending ? (
          <ActivityIndicator />
        ) : (
          <Text className="text-lg font-bold text-black">Comentar</Text>
        )}
      </Pressable>

      {errorMessage && <Text className="text-red-400 text-center mt-2">{errorMessage}</Text>}
    </View>
  );
}
