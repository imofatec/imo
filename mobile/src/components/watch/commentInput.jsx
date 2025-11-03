import React, { useState, useCallback, useMemo } from "react";
import { View, Text, TextInput, Pressable, ActivityIndicator } from "react-native";

export default function CommentInput({
  onSubmit,
  placeholder = "Adicionar comentário...",
  minLength = 2,
  maxLength = 500,
  autoFocus = false,
  onFocusInput,
}) {
  const [text, setText] = useState("");
  const [sending, setSending] = useState(false);

  const len = text.trim().length;

  const error =
    len > 0 && len < minLength
      ? `Mínimo de ${minLength} caracteres`
      : len > maxLength
      ? `Máximo de ${maxLength} caracteres`
      : null;

  const canSend = useMemo(() => {
    return !sending && len >= minLength && len <= maxLength;
  }, [sending, len, minLength, maxLength]);

  const handleSend = useCallback(async () => {
    const value = text.trim();
    if (!canSend || !value) return;

    try {
      setSending(true);
      await onSubmit?.(value);
      setText("");
    } finally {
      setSending(false);
    }
  }, [canSend, onSubmit, text]);

  return (
    <View className="px-2 pt-2 pb-4 bg-white/0">
      <View className="bg-white/5 border border-white/10 rounded-2xl p-3">
        <TextInput
          multiline
          autoFocus={autoFocus}
          value={text}
          onChangeText={setText}
          placeholder={placeholder}
          placeholderTextColor="#A3A3A3"
          className="text-white text-base"
          maxLength={maxLength + 10}
          onFocus={onFocusInput}
        />

        <View className="mt-2 flex-row items-center justify-between">
          <Text className={`text-xs ${error ? "text-red-400" : "text-white/60"}`}>
            {error ? error : "Comentários são vigiados pela moderação!"}
          </Text>

          <Text className={`text-xs ${len > maxLength ? "text-red-400" : "text-white/60"}`}>
            {len}/{maxLength}
          </Text>
        </View>
      </View>

      <Pressable
        onPress={handleSend}
        disabled={!canSend}
        className={`mt-3 py-3 rounded-full items-center ${
          canSend ? "bg-green-400" : "bg-white/10"
        }`}
      >
        {sending ? (
          <ActivityIndicator />
        ) : (
          <Text className={`text-lg font-bold ${canSend ? "text-black" : "text-white/70"}`}>
            Comentar
          </Text>
        )}
      </Pressable>
    </View>
  );
}
