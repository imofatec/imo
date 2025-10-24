import { Pressable, Animated } from 'react-native';

export default function DrawerOverlay({ opacity, onClose }) {
    return (
        <Animated.View style={{ opacity }} className="absolute top-0 left-0 w-screen h-screen z-10">
            <Pressable className="w-full h-full bg-gray-800 opacity-50" onPress={onClose} />
        </Animated.View>
    );
}