import React, { useCallback, useRef } from "react";
import { View, Text } from "react-native";
import YoutubePlayer from "react-native-youtube-iframe";

export default function PlayerHeader({ youtubeId, title, description, onEnded }) {
  const playerRef = useRef(null);

  const onChangeState = useCallback(
    (state) => {
      if (state === "ended") {
        console.log("Vídeo terminou");
        if (onEnded) onEnded();
      }
    },
    [onEnded]
  );

  return (
    <View className="mb-4">
      <View className="w-full h-56 bg-black overflow-hidden rounded-xl">
        <YoutubePlayer
          ref={playerRef}
          height={220}
          videoId={youtubeId}
          play={false}
          webViewProps={{
            allowsFullscreenVideo: true,
            domStorageEnabled: true,
          }}
          onChangeState={onChangeState}
        />
      </View>

      <View className="px-2 mt-4">
        <Text className="text-white text-2xl font-bold">{title}</Text>
        {description ? (
          <Text className="text-gray-300 mt-2">{description}</Text>
        ) : null}
      </View>
    </View>
  );
}
